package xpu.ctrl.docker.controller.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xpu.ctrl.docker.controller.HostInstanceController;
import xpu.ctrl.docker.controller.cluster.ClusterController;
import xpu.ctrl.docker.controller.cluster.CreateClusterForm;
import xpu.ctrl.docker.core.ssh.DestHost;
import xpu.ctrl.docker.core.ssh.SSHUtils;
import xpu.ctrl.docker.entity.HostLicense;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/permission-host")
public class PermissionHostInstanceController {
    @Autowired
    private HostInstanceController hostInstanceController;

    @PostMapping("remove")
    public ResultVO removeHost(String ip, @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return hostInstanceController.removeHost(ip);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }
}