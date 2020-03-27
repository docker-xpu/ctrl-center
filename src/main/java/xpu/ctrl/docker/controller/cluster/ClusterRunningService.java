package xpu.ctrl.docker.controller.cluster;

import xpu.ctrl.docker.vo.ClusterDetailInfoVO;

public interface ClusterRunningService {
    ClusterDetailInfoVO getOneClusterRunningInfo(String clusterId);
}
