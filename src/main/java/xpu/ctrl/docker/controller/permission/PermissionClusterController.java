package xpu.ctrl.docker.controller.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xpu.ctrl.docker.controller.cluster.*;

import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@Slf4j
@RestController
@RequestMapping("/permission-cluster")
public class PermissionClusterController {
    @Autowired
    private ClusterController clusterController;

    @PostMapping("adjust")
    public ResultVO adjustContainerOfCluster(String clusterId, Integer newSize,
                                             @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return clusterController.adjustContainerOfCluster(clusterId, newSize);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }

    @PostMapping("stop")
    public ResultVO stopAllContainerOfCluster(String cluster,
                                              @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return clusterController.stopAllContainerOfCluster(cluster);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }

    @PostMapping("start")
    public ResultVO startAllContainerOfCluster(String cluster, @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return clusterController.startAllContainerOfCluster(cluster);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }

    @PostMapping("remove")
    public ResultVO remove(String cluster, @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return clusterController.remove(cluster);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }

    @PostMapping("create")
    public ResultVO createOneCluster(@RequestBody CreateClusterForm createForm, @CookieValue(value = "userId") String userId) throws Exception{
        if("admin".equals(userId)){
            return clusterController.createOneCluster(createForm);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }
}