package xpu.ctrl.docker.controller.dispatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("dispatch")
public class DispatchController {
    @Autowired
    private DispatchService dispatchService;

    @GetMapping
    public void get(){
        dispatchService.autoMonitorContainer();
    }
}
