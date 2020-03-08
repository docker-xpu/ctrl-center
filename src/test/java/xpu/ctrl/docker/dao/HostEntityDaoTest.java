package xpu.ctrl.docker.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xpu.ctrl.docker.entity.HostEntity;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HostEntityDaoTest {
    @Resource
    private HostEntityDao hostEntityDao;

    @Test
    public void queryAllByLimit() {
        HostEntity hostEntity = hostEntityDao.queryById(12121);
        System.out.println(hostEntity);
    }
}