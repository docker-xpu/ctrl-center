package xpu.ctrl.docker.core.auto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xpu.ctrl.docker.core.flush.FlushHostInfoService;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.repository.HostEntityRepository;


import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AutoFlushCore implements ApplicationRunner {
    @Autowired
    private FlushHostInfoService flushHostInfoService;

    @Autowired
    private HostEntityRepository hostEntityRepository;

    @Override
    public void run(ApplicationArguments args) {

        List<HostEntity> entityList = hostEntityRepository.findAll();


        new Thread(()->{
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flushHostInfoService.flushHostInfoToStore();
            }
        }).start();

        new Thread(()->{
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取已连接主机
                for(HostEntity hostEntity: entityList){
                    //flushHostInfoService.flushHostInfoToCatch("192.168.0.102");
                    flushHostInfoService.flushHostInfoToCatch(hostEntity.getHostIp());
                }
            }
        }).start();
    }
}
