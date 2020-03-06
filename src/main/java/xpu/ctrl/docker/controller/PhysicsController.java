package xpu.ctrl.docker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/physics")
public class PhysicsController {
    @GetMapping("create-page")
    public ModelAndView newPhysicsMachine(){
        return new ModelAndView("physics/create");
    }

    @GetMapping("index")
    public ModelAndView getIndex(){
        return new ModelAndView("physics/index");
    }
}