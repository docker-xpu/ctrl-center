package xpu.ctrl.docker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@Controller
@RequestMapping("/container")
public class ContainerController {
    @GetMapping("list")
    public ResultVO getList(){

        return ResultVOUtil.success();
    }
}