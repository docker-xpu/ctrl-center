package xpu.ctrl.docker.controller.cluster.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.controller.cluster.ClusterRunningService;
import xpu.ctrl.docker.entity.HostCluster;
import xpu.ctrl.docker.repository.ClusterInfoRepository;
import xpu.ctrl.docker.repository.HostClusterRepository;
import xpu.ctrl.docker.vo.ClusterDetailInfoVO;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
public class ClusterRunningServiceImpl implements ClusterRunningService {
    @Autowired
    private ClusterInfoRepository clusterInfoRepository;

    @Autowired
    private HostClusterRepository hostClusterRepository;

    @Override
    public ClusterDetailInfoVO getOneClusterRunningInfo(String clusterId) {
        if(!clusterInfoRepository.findById(clusterId).isPresent()) return null;
        List<HostCluster> hostClusterList = hostClusterRepository.findAllByPodId(clusterId);
        for(HostCluster hostCluster: hostClusterList){
            String url = String.format("http://%s:8080/api/container/stat/?container_name=%s", hostCluster.getIp(), hostCluster.getContainerName());
            try {
                ContainerStatus containerStatus = JSONObject.parseObject(IOUtils.toString(new URL(url))).getObject("data", ContainerStatus.class);
                log.info("【container info】{}", containerStatus);
                log.info("【container info】{}", JSONObject.toJSONString(containerStatus));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
