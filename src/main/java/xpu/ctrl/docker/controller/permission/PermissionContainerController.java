package xpu.ctrl.docker.controller.permission;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.controller.container.*;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@Slf4j
@RestController
@RequestMapping("/permission-container")
public class PermissionContainerController {
    @Autowired
    private ContainerController containerController;

    @PostMapping("create")
    public String create(@org.springframework.web.bind.annotation.RequestBody CreateFormBig createFormBig,
                         @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return containerController.create(createFormBig);
        }else {
            return JSONObject.toJSONString(ResultVOUtil.error(1, "权限拒绝"));
        }
    }

    @PostMapping("start")
    public ResultVO start(String ip, String container_name, @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return containerController.start(ip, container_name);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }

    @PostMapping("stop")
    public ResultVO stop(String ip, String container_name, @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return containerController.stop(ip, container_name);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }

    @PostMapping("delete")
    public ResultVO delete(String ip, String container_name, Boolean bol, @CookieValue(value = "userId") String userId){
        if("admin".equals(userId)){
            return containerController.delete(ip, container_name, bol);
        }else {
            return ResultVOUtil.error(1, "权限拒绝");
        }
    }
}
