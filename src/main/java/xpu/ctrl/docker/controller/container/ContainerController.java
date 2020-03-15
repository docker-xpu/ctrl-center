package xpu.ctrl.docker.controller.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@Slf4j
@RestController
@RequestMapping("/container")
public class ContainerController {
    @GetMapping("list")
    public ResultVO list(){
        return ResultVOUtil.success();
    }

    @PostMapping("create")
    public ResultVO create(){
        return ResultVOUtil.success();
    }
}
