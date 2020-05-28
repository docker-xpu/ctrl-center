package xpu.ctrl.docker.controller.cluster.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.controller.cluster.ClusterRunningService;
import xpu.ctrl.docker.entity.ClusterInfo;
import xpu.ctrl.docker.entity.HostCluster;
import xpu.ctrl.docker.enums.RunStatusEnum;
import xpu.ctrl.docker.repository.ClusterInfoRepository;
import xpu.ctrl.docker.repository.HostClusterRepository;
import xpu.ctrl.docker.vo.ClusterDetailInfoVO;
import xpu.ctrl.docker.vo.HostTree;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClusterRunningServiceImpl implements ClusterRunningService {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat yearDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ClusterInfoRepository clusterInfoRepository;

    @Autowired
    private HostClusterRepository hostClusterRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveAllClusterContainerRunningInfo() {
        //不能查询所有的数据
        List<ClusterInfo> allByStatus = clusterInfoRepository.findAllByStatus(RunStatusEnum.RUNNING.getCode());
        List<HostCluster> hostClusters = Lists.newArrayList();
        for(ClusterInfo clusterInfo: allByStatus){
            if(clusterInfo.getStatus().equals(RunStatusEnum.RUNNING.getCode())){
                List<HostCluster> allByPodId = hostClusterRepository.findAllByPodId(clusterInfo.getId());
                hostClusters.addAll(allByPodId);
            }
        }
        if(hostClusters.size() == 0) return;
        for(HostCluster hostCluster: hostClusters){
            getOneContainerRunning(hostCluster.getIp(), hostCluster.getContainerName(), -1);
        }
    }

    @Override
    public ClusterDetailInfoVO getOneClusterRunningInfo(String clusterId, Integer number) {
        Optional<ClusterInfo> clusterInfoOptional = clusterInfoRepository.findById(clusterId);
        if(clusterInfoOptional.isPresent()){
            ClusterInfo clusterInfo = clusterInfoOptional.get();
            if(clusterInfo.getStatus().equals(RunStatusEnum.STOP.getCode())){
                ClusterDetailInfoVO clusterDetailInfoVO = new ClusterDetailInfoVO();
                BeanUtils.copyProperties(clusterInfo, clusterDetailInfoVO);
                clusterDetailInfoVO.setStatus(RunStatusEnum.STOP.getCode());
                clusterDetailInfoVO.setCreateTimeStr(clusterDetailInfoVO.getCreateTime()+"");
                clusterDetailInfoVO.setGateWayIp("192.168.2.2");
                clusterDetailInfoVO.setStatusStr(RunStatusEnum.STOP.getMessage());
                clusterDetailInfoVO.setAvgLoad(null);
                clusterDetailInfoVO.setHosts(null);
                clusterDetailInfoVO.setHostTree(null);
                return clusterDetailInfoVO;
            }
        }

        if(!clusterInfoOptional.isPresent()) return null;
        ClusterDetailInfoVO clusterDetailInfoVORet = (ClusterDetailInfoVO) redisTemplate.opsForValue().get(String.format("%s_running_info", clusterId));
        List<HostCluster> hostClusterList = hostClusterRepository.findAllByPodId(clusterId);

        //CPU和Mem负载对象
        ClusterDetailInfoVO.AvgLoadBean avgLoadBean;
        ClusterDetailInfoVO.AvgLoadBean.MemBean memBean;
        ClusterDetailInfoVO.AvgLoadBean.CpuBean cpuBean;
        if(clusterDetailInfoVORet == null){
            //first one
            clusterDetailInfoVORet = new ClusterDetailInfoVO();
            ClusterInfo clusterInfo = clusterInfoOptional.get();
            BeanUtils.copyProperties(clusterInfo, clusterDetailInfoVORet);
            clusterDetailInfoVORet.setCreateTimeStr(String.valueOf(clusterInfo.getCreateTime()));
            clusterDetailInfoVORet.setGateWayIp("192.168.2.2");

            //开始构造AvgLoadBean对象
            avgLoadBean = new ClusterDetailInfoVO.AvgLoadBean();
            memBean = new ClusterDetailInfoVO.AvgLoadBean.MemBean();
            cpuBean = new ClusterDetailInfoVO.AvgLoadBean.CpuBean();

            memBean.setData(new LinkedList<>());
            memBean.setXAxis(new LinkedList<>());
            cpuBean.setData(new LinkedList<>());
            cpuBean.setXAxis(new LinkedList<>());

            //初始化队列填充到avgLoadBean中
            avgLoadBean.setMem(memBean);
            avgLoadBean.setCpu(cpuBean);
        }else{
            avgLoadBean = clusterDetailInfoVORet.getAvgLoad();
            memBean = avgLoadBean.getMem();
            cpuBean = avgLoadBean.getCpu();
        }

        LinkedList<Double> memDoubleLinked = memBean.getData();
        LinkedList<String> memXAxisLinked = memBean.getXAxis();
        LinkedList<Double> cpuDoubleLinked = cpuBean.getData();
        LinkedList<String> cpuXAxisLinked = cpuBean.getXAxis();

        double cpuPercent = 0.0;
        double memPercent = 0.0;
        String timeNow = simpleDateFormat.format(new Date());

        Set<String> ipSet = Sets.newHashSet();
        //把最新的数据刷入队列
        for(HostCluster hostCluster: hostClusterList){
            ipSet.add(hostCluster.getIp());
            String url = String.format("http://%s:8080/api/container/stat/?container_name=%s", hostCluster.getIp(), hostCluster.getContainerName());
            try {
                ContainerStatus containerStatus = JSONObject.parseObject(IOUtils.toString(new URL(url))).getObject("data", ContainerStatus.class);
                ContainerStatus.CpuStatsBean.CpuUsageBean cpu_usage = containerStatus.getCpu_stats().getCpu_usage();
                long cpuDelta = cpu_usage.getTotal_usage() - averagePerUsage(cpu_usage.getPercpu_usage());
                long system_cpu_usage = containerStatus.getCpu_stats().getSystem_cpu_usage();
                //单容器CPU使用率和Mem使用率
                cpuPercent = (((double)cpuDelta / system_cpu_usage) + cpuPercent) / 2;
                BigDecimal bigDecimal = new BigDecimal(cpuPercent);
                cpuPercent = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                memPercent = ((double) containerStatus.getMemory_stats().getUsage() / containerStatus.getMemory_stats().getLimit() + memPercent) / 2;
                bigDecimal = new BigDecimal(memPercent);
                memPercent = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        if(memDoubleLinked.size() >= 100) {
            memDoubleLinked.removeLast();
            memXAxisLinked.removeLast();
        }
        memDoubleLinked.addFirst(memPercent);
        memXAxisLinked.addFirst(timeNow);

        if(cpuDoubleLinked.size() >= 100){
            cpuDoubleLinked.removeLast();
            cpuXAxisLinked.removeLast();
        }

        cpuDoubleLinked.addFirst(cpuPercent);
        cpuXAxisLinked.addFirst(timeNow);

        cpuBean.setData(cpuDoubleLinked);
        cpuBean.setXAxis(cpuXAxisLinked);
        memBean.setData(memDoubleLinked);
        memBean.setXAxis(memXAxisLinked);

        avgLoadBean.setCpu(cpuBean);
        avgLoadBean.setMem(memBean);

        //得出数据
        clusterDetailInfoVORet.setAvgLoad(avgLoadBean);
        clusterDetailInfoVORet.setStatus(RunStatusEnum.RUNNING.getCode());
        clusterDetailInfoVORet.setStatusStr(RunStatusEnum.RUNNING.getMessage());
        String hostTreeFormat = yearDateFormat.format(new Date(clusterDetailInfoVORet.getCreateTime()));

        //开始构造HostInfo对象
        List<ClusterDetailInfoVO.HostsBean> retHostsBean = Lists.newArrayListWithCapacity(ipSet.size());
        ClusterDetailInfoVO.HostsBean hostsBean;
        HostTree.ChildrenBeanX childrenBeanX;
        List<HostTree.ChildrenBeanX> childrenBeanXList = Lists.newArrayList();
        for(String ip: ipSet){
            hostsBean = new ClusterDetailInfoVO.HostsBean();
            List<HostCluster> hostClusters = hostClusterRepository.findAllByPodIdAndIp(clusterId, ip);
            hostsBean.setIp(ip);
            List<String> collect = hostClusters.stream().map(HostCluster::getContainerName).collect(Collectors.toList());
            hostsBean.setContainers(collect);
            retHostsBean.add(hostsBean);

            childrenBeanX = new HostTree.ChildrenBeanX();
            childrenBeanX.setName(ip);
            List<HostTree.ChildrenBeanX.ChildrenBean> childrenBeanList = Lists.newArrayList();
            for(HostCluster hostCluster: hostClusters){
                HostTree.ChildrenBeanX.ChildrenBean childrenBean = new HostTree.ChildrenBeanX.ChildrenBean();
                childrenBean.setName(hostCluster.getContainerName());
                childrenBean.setValue(hostTreeFormat);
                childrenBeanList.add(childrenBean);
            }
            childrenBeanX.setChildren(childrenBeanList);
            childrenBeanXList.add(childrenBeanX);
        }
        clusterDetailInfoVORet.setHosts(retHostsBean);

        HostTree hostTree = new HostTree();
        hostTree.setName(clusterDetailInfoVORet.getPodName());
        hostTree.setChildren(childrenBeanXList);

        clusterDetailInfoVORet.setHostTree(hostTree);

        clusterDetailInfoVORet.setNodeNumber(clusterInfoOptional.get().getNodeNumber());
        redisTemplate.opsForValue().set(String.format("%s_running_info", clusterId), clusterDetailInfoVORet, 60 * 10, TimeUnit.SECONDS);

        //返回合适的avgLoadBean
        LinkedList<Double> cpuData = avgLoadBean.getCpu().getData();
        LinkedList<String> cpuXAxis = avgLoadBean.getCpu().getXAxis();
        LinkedList<Double> memData = avgLoadBean.getMem().getData();
        LinkedList<String> memXAxis = avgLoadBean.getMem().getXAxis();

        while(cpuData.size() > number){
            cpuData.removeLast();
            cpuXAxis.removeLast();
            memData.removeLast();
            memXAxis.removeLast();
        }
        ClusterDetailInfoVO.AvgLoadBean.MemBean memBean1 = new ClusterDetailInfoVO.AvgLoadBean.MemBean();
        memBean1.setXAxis(memXAxis);
        memBean1.setData(memData);
        ClusterDetailInfoVO.AvgLoadBean.CpuBean cpuBean1 = new ClusterDetailInfoVO.AvgLoadBean.CpuBean();
        cpuBean1.setXAxis(cpuXAxis);
        cpuBean1.setData(cpuData);
        avgLoadBean.setMem(memBean1);
        avgLoadBean.setCpu(cpuBean1);

        clusterDetailInfoVORet.setAvgLoad(avgLoadBean);
        return clusterDetailInfoVORet;
    }

    @Override
    public ClusterDetailInfoVO.AvgLoadBean getOneContainerRunning(String ip, String containerId, Integer number) {
        String url = String.format("http://%s:8080/api/container/stat/?container_name=%s", ip, containerId);
        ContainerStatus containerStatus;
        String redisKey = String.format("%s-%s-container", ip, containerId);
        ClusterDetailInfoVO.AvgLoadBean avgLoadBean = (ClusterDetailInfoVO.AvgLoadBean) redisTemplate.opsForValue().get(redisKey);
        ClusterDetailInfoVO.AvgLoadBean.CpuBean cpuBean;
        ClusterDetailInfoVO.AvgLoadBean.MemBean memBean;
        if(avgLoadBean == null){
            avgLoadBean = new ClusterDetailInfoVO.AvgLoadBean();
            memBean = new ClusterDetailInfoVO.AvgLoadBean.MemBean();
            cpuBean = new ClusterDetailInfoVO.AvgLoadBean.CpuBean();

            memBean.setXAxis(new LinkedList<>());
            memBean.setData(new LinkedList<>());
            cpuBean.setData(new LinkedList<>());
            cpuBean.setXAxis(new LinkedList<>());

            avgLoadBean.setCpu(cpuBean);
            avgLoadBean.setMem(memBean);
        }else{
            memBean = avgLoadBean.getMem();
            cpuBean = avgLoadBean.getCpu();
        }

        String timeNow = simpleDateFormat.format(new Date());
        LinkedList<Double> memBeanData = memBean.getData();
        LinkedList<String> memBeanXAxis = memBean.getXAxis();

        LinkedList<Double> cpuBeanData = cpuBean.getData();
        LinkedList<String> cpuBeanXAxis = cpuBean.getXAxis();

        double cpuPercent;
        double memPercent;

        try {
            containerStatus = JSONObject.parseObject(IOUtils.toString(new URL(url))).getObject("data", ContainerStatus.class);
            ContainerStatus.CpuStatsBean.CpuUsageBean cpu_usage = containerStatus.getCpu_stats().getCpu_usage();
            long cpuDelta = cpu_usage.getTotal_usage() - averagePerUsage(cpu_usage.getPercpu_usage());
            long system_cpu_usage = containerStatus.getCpu_stats().getSystem_cpu_usage();

            //单容器CPU使用率和Mem使用率
            cpuPercent = ((double)cpuDelta / system_cpu_usage);
            memPercent = (double) containerStatus.getMemory_stats().getUsage() / containerStatus.getMemory_stats().getLimit();
            BigDecimal bigDecimal = new BigDecimal(cpuPercent);
            cpuPercent = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            bigDecimal = new BigDecimal(memPercent);
            memPercent = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if(memBeanData.size() > 100){
                memBeanData.removeLast();
                memBeanXAxis.removeLast();
            }
            memBeanData.add(memPercent);
            memBeanXAxis.add(timeNow);

            if(cpuBeanData.size() > 100){
                cpuBeanData.removeLast();
                cpuBeanXAxis.removeLast();
            }
            cpuBeanData.add(cpuPercent);
            cpuBeanXAxis.add(timeNow);
        } catch (IOException e) {
            return null;
        }

        memBean.setData(memBeanData);
        memBean.setXAxis(memBeanXAxis);
        cpuBean.setData(cpuBeanData);
        cpuBean.setXAxis(cpuBeanXAxis);

        avgLoadBean.setMem(memBean);
        avgLoadBean.setCpu(cpuBean);

        redisTemplate.opsForValue().set(redisKey, avgLoadBean, 60*10, TimeUnit.SECONDS);

        if(number > 0){
            //返回合适的avgLoadBean
            LinkedList<Double> cpuData = avgLoadBean.getCpu().getData();
            LinkedList<String> cpuXAxis = avgLoadBean.getCpu().getXAxis();
            LinkedList<Double> memData = avgLoadBean.getMem().getData();
            LinkedList<String> memXAxis = avgLoadBean.getMem().getXAxis();

            while(cpuData.size() > number){
                cpuData.removeLast();
                cpuXAxis.removeLast();
                memData.removeLast();
                memXAxis.removeLast();
            }
            ClusterDetailInfoVO.AvgLoadBean.MemBean memBean1 = new ClusterDetailInfoVO.AvgLoadBean.MemBean();
            memBean1.setXAxis(memXAxis);
            memBean1.setData(memData);
            ClusterDetailInfoVO.AvgLoadBean.CpuBean cpuBean1 = new ClusterDetailInfoVO.AvgLoadBean.CpuBean();
            cpuBean1.setXAxis(cpuXAxis);
            cpuBean1.setData(cpuData);

            avgLoadBean.setMem(memBean1);
            avgLoadBean.setCpu(cpuBean1);
        }
        return avgLoadBean;
    }

    private long averagePerUsage(List<Long> per_cpu_usage) {
        long ret = 0;
        for(Long num: per_cpu_usage){
            if(ret < num){
                ret = (num - ret)/2 + ret;
            }else{
                ret = (ret - num)/2 + num;
            }
        }
        return ret;
    }
}