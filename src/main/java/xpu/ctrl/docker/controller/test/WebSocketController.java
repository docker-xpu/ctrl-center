package xpu.ctrl.docker.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class WebSocketController {
    @GetMapping
    public ModelAndView getIndexTest(){
        return new ModelAndView("websocket");
    }
}
