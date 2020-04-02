package xpu.ctrl.docker.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xpu.ctrl.docker.entity.StorageInfo;

import java.util.logging.StreamHandler;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class StorageInfoRepositoryTest {
    @Autowired
    private StorageInfoRepository storageInfoRepository;

    @Test
    public void save(){
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setCreateTime(System.currentTimeMillis());
        storageInfo.setContent("XXXXXXXXXXXXXX");
        assertNotNull(storageInfoRepository.save(storageInfo));
    }
}