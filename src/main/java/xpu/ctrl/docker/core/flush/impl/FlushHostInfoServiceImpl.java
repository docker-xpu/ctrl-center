package xpu.ctrl.docker.core.flush.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.core.flush.FlushHostInfoService;
import xpu.ctrl.docker.core.flush.HostEntityHardWare;
import xpu.ctrl.docker.core.util.HostInfoToObjectUtil;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.enums.RunStatusEnum;
import xpu.ctrl.docker.repository.HostEntityHardWareRepository;
import xpu.ctrl.docker.repository.HostEntityRepository;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class FlushHostInfoServiceImpl implements FlushHostInfoService {
    @Autowired
    private HostEntityHardWareRepository hostEntityHardWareRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HostEntityRepository hostEntityRepository;

    @Override
    public void flushHostInfoToCatch(String ip) {
        try {
            String jsonString = IOUtils.toString(new URL(String.format("http://%s:8080/api/host/info", ip)), StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONObject.parseObject(jsonString).getJSONObject("data");
            HostEntityHardWare hardWare = HostInfoToObjectUtil.jsonToHostEntityHardWare(jsonObject);
            hardWare.setIp(ip);
            System.out.println(hardWare);
            redisTemplate.opsForValue().set(String.format("hardWare%s", hardWare.getUpdateTime()), hardWare);
        } catch (IOException e) {
            Optional<HostEntity> entity = hostEntityRepository.findById(ip);
            if(entity.isPresent()){
                HostEntity hostEntity = entity.get();
                hostEntity.setHostStatus(RunStatusEnum.STOP.getCode());
                hostEntityRepository.save(hostEntity);
            }
            e.printStackTrace();
        }
    }

    @Override
    public void flushHostInfoToStore() {
        Set<String> keys = redisTemplate.keys("hardWare*");
        for(String k: keys){
            Object o = redisTemplate.opsForValue().get(k);
            log.info("【反序列化化结果】{}", o);
            redisTemplate.delete(k);
            hostEntityHardWareRepository.save((HostEntityHardWare)o);
        }
    }
}