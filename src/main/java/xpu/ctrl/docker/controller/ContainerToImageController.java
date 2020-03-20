package xpu.ctrl.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@Slf4j
@RestController
@RequestMapping("/change")
public class ContainerToImageController {
    @PostMapping("to-image")
    public ResultVO toImage(){
        return ResultVOUtil.success();
    }
}
