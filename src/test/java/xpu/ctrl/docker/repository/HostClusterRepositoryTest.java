package xpu.ctrl.docker.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xpu.ctrl.docker.entity.HostCluster;

import static org.junit.Assert.*;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class HostClusterRepositoryTest {
    @Autowired
    private HostClusterRepository hostClusterRepository;

    @Test
    public void save(){
        HostCluster hostCluster = new HostCluster();
        hostCluster.setPodId("xxx");
        hostCluster.setPort(90);
        hostCluster.setIp("192.178.2.2");
        assertNotNull(hostClusterRepository.save(hostCluster));
    }
}