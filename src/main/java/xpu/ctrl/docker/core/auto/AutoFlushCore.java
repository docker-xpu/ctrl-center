package xpu.ctrl.docker.core.auto;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xpu.ctrl.docker.controller.cluster.ClusterRunningController;
import xpu.ctrl.docker.controller.cluster.ClusterRunningService;
import xpu.ctrl.docker.controller.dispatch.DispatchService;
import xpu.ctrl.docker.core.flush.FlushHostInfoService;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.repository.HostEntityRepository;
import xpu.ctrl.docker.service.GetHostInfoWebSocket;
import xpu.ctrl.docker.service.HostEntityService;
import xpu.ctrl.docker.vo.HostRunningVO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AutoFlushCore implements ApplicationRunner {
    @Autowired
    private FlushHostInfoService flushHostInfoService;

    @Autowired
    private HostEntityRepository hostEntityRepository;

    @Autowired
    private HostEntityService hostEntityService;

    @Autowired
    private ClusterRunningController clusterRunningController;

    @Autowired
    private ClusterRunningService clusterRunningService;

    @Autowired
    private DispatchService dispatchService;

    @Override
    public void run(ApplicationArguments args) {
        new Thread(()->{
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flushHostInfoService.flushHostInfoToStore();
            }
        }).start();

        new Thread(()->{
            while (true) {
                List<HostEntity> entityList = hostEntityRepository.findAll();
                //获取已连接主机
                for(HostEntity hostEntity: entityList){
                    //flushHostInfoService.flushHostInfoToCatch("192.168.0.102");
                    flushHostInfoService.flushHostInfoToCatch(hostEntity.getHostIp());
                }
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            while (true){
                List<HostRunningVO> runningHost = hostEntityService.getRunningHost();
                //log.info("【WebSocket数据】{}", runningHost);
                CopyOnWriteArrayList<GetHostInfoWebSocket> webSocketSet = GetHostInfoWebSocket.webSocketSet;
                for(GetHostInfoWebSocket getHostInfoWebSocket: webSocketSet){
                    getHostInfoWebSocket.sendMessage(JSONObject.toJSONString(runningHost));
                }

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            while (true){
                clusterRunningController.getAllRunningVO(100);
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            while (true){
                clusterRunningService.saveAllClusterContainerRunningInfo();
                try {
                    TimeUnit.SECONDS.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("检测存活");
                    dispatchService.autoMonitorContainer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}