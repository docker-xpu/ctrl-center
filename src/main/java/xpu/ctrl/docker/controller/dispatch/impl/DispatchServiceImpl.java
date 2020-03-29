package xpu.ctrl.docker.controller.dispatch.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xpu.ctrl.docker.controller.container.ContainerController;
import xpu.ctrl.docker.controller.container.CreateForm;
import xpu.ctrl.docker.controller.container.CreateFormBig;
import xpu.ctrl.docker.controller.dispatch.DispatchService;
import xpu.ctrl.docker.entity.ContainerCreate;
import xpu.ctrl.docker.entity.HostCluster;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.entity.MigrateInfo;
import xpu.ctrl.docker.enums.RunStatusEnum;
import xpu.ctrl.docker.repository.*;
import xpu.ctrl.docker.service.HostEntityService;
import xpu.ctrl.docker.vo.HostEntityVO;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DispatchServiceImpl implements DispatchService {
    @Autowired
    private HostEntityRepository hostEntityRepository;

    @Autowired
    private ContainerController containerController;

    @Autowired
    private ContainerCreateRepository containerCreateRepository;

    @Autowired
    private HostEntityService hostEntityService;

    @Autowired
    private ClusterInfoRepository clusterInfoRepository;

    @Autowired
    private HostClusterRepository hostClusterRepository;

    @Autowired
    private MigrateInfoRepository migrateInfoRepository;

    @Override
    public void autoMonitorContainer() {
        List<ContainerCreate> needMigrateContainer = Lists.newArrayList();
        List<HostEntity> stoppedHostList = hostEntityRepository.findAllByHostStatus(RunStatusEnum.STOP.getCode());
        for(HostEntity hostEntity: stoppedHostList){
            String hostIp = hostEntity.getHostIp();
            needMigrateContainer.addAll(containerCreateRepository.findAllByHostIp(hostIp));
        }
        Set<String> clusterSet = new HashSet<>();
        Map<String, List<Migrate>> clusterIpAndPort = new HashMap<>();
        List<Migrate> list;
        //拿到了需要迁移的容器（非集群容器 + 集群容器）
        for(ContainerCreate containerCreate: needMigrateContainer){
            String clusterId = containerCreate.getClusterId();
            if(StringUtils.isEmpty(clusterId)){
                //非集群容器
                String createFrom = containerCreate.getCreateFrom();
                CreateFormBig createFormBig = JSONObject.parseObject(createFrom, CreateFormBig.class);
                String oldIp = createFormBig.getIp();
                String oldHostPort = createFormBig.getCreateForm().getHost_port();
                //0、获取可用主机（按负载排个序）
                List<HostEntityVO> runningHostByLoad = hostEntityService.getRunningHostByLoad(1);
                String newIp = runningHostByLoad.get(0).getHostIp();
                createFormBig.setIp(newIp);

                //获取新的可用端口
                Integer newPort = 0;
                try {
                    URL url = new URL(String.format("http://%s:8080//api/host/ports/", newIp));
                    PortResultInDispatch portResult = JSONArray.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8), PortResultInDispatch.class);
                    List<Integer> resultData = portResult.getData();
                    newPort = resultData.get(0);
                    CreateForm createForm = createFormBig.getCreateForm();
                    createForm.setHost_port(newPort+"");
                    createFormBig.setCreateForm(createForm);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //记录迁移日志
                String migrateResult = containerController.create(createFormBig);
                MigrateInfo migrateInfo = new MigrateInfo();
                migrateInfo.setMigrateId(System.currentTimeMillis());
                migrateInfo.setMigrateLog(String.format("单容器迁移 [src-IPAndPort :%s:%s --> desc-IPAndPort:%s:%s], %s",
                        oldIp, oldHostPort,
                        newIp, newPort, migrateResult));
                migrateInfoRepository.save(migrateInfo);

                //开始迁移
                containerController.create(createFormBig);

                //启动新容器
                containerController.start(newIp, createFormBig.getCreateForm().getContainer_name());

                //删除原来的绑定地址
                containerCreateRepository.deleteById(oldIp + createFormBig.getCreateForm().getContainer_name());
            }else{
//                if(clusterSet.add(clusterId)){
//                    //之前没存
//                    list = Lists.newArrayList();
//                    clusterIpAndPort.put(clusterId, list);
//                }else{
//                    //存了
//                    list = clusterIpAndPort.get(clusterId);
//                }
//                //集群容器
//                //clusterId;
//                String createFrom = containerCreate.getCreateFrom();
//                CreateFormBig createFormBig = JSONObject.parseObject(createFrom, CreateFormBig.class);
//                String oldIp = createFormBig.getIp();
//                String oldHostPort = createFormBig.getCreateForm().getHost_port();
//                //清空对应关系
//                hostClusterRepository.deleteAllByPodIdAndIp(clusterId, containerCreate.getHostIp());
//
//                //新建容器
//                //0、获取可用主机（按负载排个序）
//                List<HostEntityVO> runningHostByLoad = hostEntityService.getRunningHostByLoad(1);
//                String newIp = runningHostByLoad.get(0).getHostIp();
//                //获取新的可用端口
//                Integer newPort = 0;
//                try {
//                    URL url = new URL(String.format("http://%s:8080//api/host/ports/", newIp));
//                    PortResultInDispatch portResult = JSONArray.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8), PortResultInDispatch.class);
//                    List<Integer> resultData = portResult.getData();
//                    newPort = resultData.get(0);
//                    CreateForm createForm = createFormBig.getCreateForm();
//                    createForm.setHost_port(newPort+"");
//                    createFormBig.setCreateForm(createForm);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                createFormBig.setIp(newIp);
////                CreateForm createForm = createFormBig.getCreateForm();
////                String container_name = createForm.getContainer_name();
//                //nginxCluster.0.192.168.2.4
//                //记录迁移日志
//                String migrateResult = containerController.create(createFormBig);
//
//                MigrateInfo migrateInfo = new MigrateInfo();
//                migrateInfo.setMigrateId(System.currentTimeMillis());
//                migrateInfo.setMigrateLog(String.format("单容器迁移 [src-IPAndPort :%s:%s --> desc-IPAndPort:%s:%s], %s",
//                        oldIp, oldHostPort,
//                        newIp, newPort, migrateResult));
//                migrateInfoRepository.save(migrateInfo);
//
//                //启动新容器
//                containerController.start(newIp, createFormBig.getCreateForm().getContainer_name());
//
//                //删除原来的绑定地址
//                containerCreateRepository.deleteById(oldIp + createFormBig.getCreateForm().getContainer_name());
//                Migrate migrate = new Migrate(clusterId, oldHostPort, oldIp, newPort+"", newIp, createFormBig.getCreateForm().getContainer_name());
//                list.add(migrate);
//                clusterIpAndPort.put(clusterId, list);
            }
        }
//        //重新配置网关
//        //Map<String, List<Migrate>> clusterIpAndPort = new HashMap<>();
//        for (String key: clusterIpAndPort.keySet()){
//            List<Migrate> migrates = clusterIpAndPort.get(key);
//            for(Migrate migrate: migrates){
//                HostCluster hostCluster = new HostCluster();
//                hostCluster.setIp(migrate.getNewIp());
//                hostCluster.setPort(Integer.parseInt(migrate.getNewPort()));
//                hostCluster.setPodId(migrate.getClusterId());
//                hostCluster.setContainerName(migrate.getContainerName());
//                hostClusterRepository.save(hostCluster);
//            }
//        }
        //配置文件
    }
}

@Data
@AllArgsConstructor
class Migrate{
    private String clusterId;
    private String oldPort;
    private String oldIp;
    private String newPort;
    private String newIp;
    private String containerName;
}

@Data
class PortResultInDispatch{
    /**
     * status : 0
     * msg : success
     * data : [10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061,10062,10063,10064,10065,10066,10067,10068,10069,10070,10071,10072,10073,10074,10075,10076,10077,10078,10079,10080,10081,10082,10083,10084,10085,10086,10087,10088,10089,10090,10091,10092,10093,10094,10095,10096,10097,10098,10099]
     */
    private int status;
    private String msg;
    private List<Integer> data;
}
