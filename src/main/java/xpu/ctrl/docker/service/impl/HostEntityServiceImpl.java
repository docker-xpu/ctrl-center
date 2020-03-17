package xpu.ctrl.docker.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import xpu.ctrl.docker.dataobject.DksvContainerInfo;
import xpu.ctrl.docker.dataobject.DksvHostInfo;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.dao.HostEntityDao;
import xpu.ctrl.docker.enums.RunStatusEnum;
import xpu.ctrl.docker.repository.HostEntityRepository;
import xpu.ctrl.docker.service.HostEntityService;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.util.EnumUtil;
import xpu.ctrl.docker.vo.HostEntityVO;
import xpu.ctrl.docker.vo.HostRunningVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * (HostEntity)表服务实现类
 *
 * @author makejava
 * @since 2020-03-12 12:29:32
 */
@Service
public class HostEntityServiceImpl implements HostEntityService {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Resource
    private HostEntityDao hostEntityDao;

    @Autowired
    private HostEntityRepository hostEntityRepository;

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private static ExecutorService cachedThreadPool2 = Executors.newCachedThreadPool();

    @Override
    public List<HostRunningVO> getRunningHost() {
        List<HostEntity> hostStatus = hostEntityRepository.findAllByHostStatus(RunStatusEnum.RUNNING.getCode());
        List<HostRunningVO> hostRunningVOList = Lists.newArrayListWithCapacity(hostStatus.size());
        final CountDownLatch countDownLatch = new CountDownLatch(hostStatus.size());
        for (HostEntity hostEntity: hostStatus){
            cachedThreadPool.execute(()->{
                HostRunningVO hostRunningVO = new HostRunningVO();
                String hostIp = hostEntity.getHostIp();
                URL url;
                try {
                    url = new URL(String.format("http://%s:8080/api/host/info", hostIp));
                    String data = JSONObject.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8)).getString("data");
                    DksvHostInfo dksvHostInfo = JSONObject.parseObject(data, DksvHostInfo.class);
                    hostRunningVO.setCpu(dksvHostInfo.getCpu_info().getPercent().get(0)/100.0);
                    hostRunningVO.setDisk(dksvHostInfo.getDisk_info().getUsedPercent()/100.0);

                    HostRunningVO.Io io = new HostRunningVO.Io();
                    DksvHostInfo.IoCountersBean ioCountersBean = dksvHostInfo.getIo_counters().get(0);
                    BigInteger packetsSent = ioCountersBean.getPacketsSent();
                    io.setPackage_sent(packetsSent.longValue());
                    io.setPackage_recv(ioCountersBean.getPacketsRecv().longValue());
                    hostRunningVO.setIo(io);

                    hostRunningVO.setTimestamp(simpleDateFormat.format(new Date(System.currentTimeMillis())));
                    hostRunningVO.setHost_ip(hostEntity.getHostIp());
                    hostRunningVO.setMem(dksvHostInfo.getMem_info().getVirtual_memory().getUsedPercent()/100.0);

                    hostRunningVOList.add(hostRunningVO);
                    countDownLatch.countDown();
                } catch (IOException e) {
                    Optional<HostEntity> repository = hostEntityRepository.findById(hostIp);
                    if(repository.isPresent()){
                        HostEntity entity = repository.get();
                        entity.setHostStatus(RunStatusEnum.STOP.getCode());
                        hostEntityRepository.save(entity);
                    }
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hostRunningVOList;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param hostIp 主键
     * @return 实例对象
     */
    @Override
    public HostEntity queryById(String hostIp) {
        return this.hostEntityDao.queryById(hostIp);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<HostEntity> queryAllByLimit(int offset, int limit) {
        return this.hostEntityDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param hostEntity 实例对象
     * @return 实例对象
     */
    @Override
    public HostEntity insert(HostEntity hostEntity) {
        this.hostEntityDao.insert(hostEntity);
        return hostEntity;
    }

    /**
     * 修改数据
     *
     * @param hostEntity 实例对象
     * @return 实例对象
     */
    @Override
    public HostEntity update(HostEntity hostEntity) {
        this.hostEntityDao.update(hostEntity);
        return this.queryById(hostEntity.getHostIp());
    }

    /**
     * 通过主键删除数据
     *
     * @param hostIp 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String hostIp) {
        return this.hostEntityDao.deleteById(hostIp) > 0;
    }

    @Override
    public List<HostEntityVO> getAllHost() {
        List<HostEntity> allByHostStatus = hostEntityRepository.findAllByHostStatus(RunStatusEnum.RUNNING.getCode());
        List<HostEntityVO> hostEntityVOList = Lists.newArrayListWithCapacity(allByHostStatus.size());
        final CountDownLatch countDownLatch = new CountDownLatch(allByHostStatus.size());
        for(HostEntity hostEntity: allByHostStatus){
            cachedThreadPool2.execute(()->{
                String hostIp = hostEntity.getHostIp();
                HostEntityVO hostEntityVO = toVO(hostEntity);
                URL url;
                try{
                    url = new URL(String.format("http://%s:8080/api/host/info", hostIp));
                    String data = JSONObject.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8)).getString("data");
                    DksvHostInfo dksvHostInfo = JSONObject.parseObject(data, DksvHostInfo.class);
                    hostEntityVO.setBootTime(dksvHostInfo.getHost_info().getBootTime());
                    hostEntityVO.setUpTime(dksvHostInfo.getHost_info().getUptime());
                    hostEntityVO.setLogical_cores(dksvHostInfo.getCpu_info().getLogical_cores());
                    hostEntityVO.setPhysical_cores(dksvHostInfo.getCpu_info().getPhysical_cores());
                    hostEntityVO.setDisk_free(dksvHostInfo.getDisk_info().getFree());
                    hostEntityVO.setDisk_total(dksvHostInfo.getDisk_info().getTotal());
                    hostEntityVO.setHostKernelVersion(dksvHostInfo.getHost_info().getKernelVersion());
                    hostEntityVO.setHostPlatformOs(dksvHostInfo.getHost_info().getPlatform());
                    hostEntityVO.setMemFree(dksvHostInfo.getMem_info().getVirtual_memory().getFree());
                    hostEntityVO.setMemTotal(dksvHostInfo.getMem_info().getVirtual_memory().getTotal());

                    //http://139.159.254.242:8080//api/container/list/
                    URL url1 = new URL(String.format("http://%s:8080//api/container/list/", hostIp));
                    String string = JSONObject.parseObject(IOUtils.toString(url1, StandardCharsets.UTF_8)).getString("data");
                    List<DksvContainerInfo> containerInfoList = JSONObject.parseArray(string, DksvContainerInfo.class);
                    hostEntityVO.setContainers(containerInfoList);
                    hostEntityVOList.add(hostEntityVO);
                    countDownLatch.countDown();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hostEntityVOList;
    }

//    private List<HostEntityVO> toVO(List<HostEntity> hostEntityList){
//        ArrayList<HostEntityVO>  hostEntityVOArrayList= Lists.newArrayListWithCapacity(hostEntityList.size());
//        for(HostEntity hostEntity: hostEntityList){
//            hostEntityVOArrayList.add(toVO(hostEntity));
//        }
//        return hostEntityVOArrayList;
//    }
    private HostEntityVO toVO(HostEntity hostEntity){
        HostEntityVO hostEntityVO = new HostEntityVO();
        BeanUtils.copyProperties(hostEntity, hostEntityVO);
        hostEntityVO.setHostStatusStr(EnumUtil.getByCode(hostEntity.getHostStatus(), RunStatusEnum.class).getMessage());
        return hostEntityVO;
    }
}