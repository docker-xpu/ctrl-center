package xpu.ctrl.docker.controller.cluster;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.entity.ClusterInfo;
import xpu.ctrl.docker.repository.ClusterInfoRepository;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ClusterDetailInfoVO;
import xpu.ctrl.docker.vo.ResultVO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cluster-running")
public class ClusterRunningController {
    @Autowired
    private ClusterRunningService clusterRunningService;

    @Autowired
    private ClusterInfoRepository clusterInfoRepository;

    @GetMapping("get-one")
    public ResultVO getOneRunningVO(String clusterId, @RequestParam(required = false, defaultValue = "100") Integer number){
        ClusterDetailInfoVO oneClusterRunningInfo = clusterRunningService.getOneClusterRunningInfo(clusterId, number);
        return ResultVOUtil.success(oneClusterRunningInfo);
    }

    @GetMapping("get-all")
    public ResultVO getAllRunningVO(@RequestParam(required = false, defaultValue = "100") Integer number){
        List<ClusterInfo> all = clusterInfoRepository.findAll();
        List<ClusterDetailInfoVO> clusterDetailInfoVOList = Lists.newArrayListWithCapacity(all.size());
        for (ClusterInfo clusterInfo: all){
            clusterDetailInfoVOList.add(clusterRunningService.getOneClusterRunningInfo(clusterInfo.getId(), number));
        }
        return ResultVOUtil.success(clusterDetailInfoVOList);
    }

    @GetMapping("get-container")
    public ResultVO getContainerRunningInfo(String ip, String containerId, @RequestParam(required = false, defaultValue = "100") Integer number){
        ClusterDetailInfoVO.AvgLoadBean clusterRunningServiceOneContainerRunning = clusterRunningService.getOneContainerRunning(ip, containerId, number);
        return ResultVOUtil.success(clusterRunningServiceOneContainerRunning);
    }
}