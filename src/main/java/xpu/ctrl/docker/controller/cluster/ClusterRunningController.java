package xpu.ctrl.docker.controller.cluster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@Slf4j
@RestController
@RequestMapping("/cluster-running")
public class ClusterRunningController {
    @Autowired
    private ClusterRunningService clusterRunningService;

    @GetMapping("get-one")
    public ResultVO getOneRunningVO(String clusterId){
        return ResultVOUtil.success(clusterRunningService.getOneClusterRunningInfo(clusterId));
    }
}