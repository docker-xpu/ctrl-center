package xpu.ctrl.docker.controller.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @RequestMapping("login")
    public ResultVO userLogin(String userId, String password, HttpServletResponse httpServletResponse){
        if(userId.equals("admin")){
            if(password.equals("admin")){
                Cookie cookie = new Cookie("userId", "admin");
                cookie.setMaxAge(60 * 60 * 10);
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
                return ResultVOUtil.success("管理员登录成功");
            }else{
                return ResultVOUtil.error(1, "密码错误");
            }
        }

        if(userId.equals(password)) {
            Cookie cookie = new Cookie("userId", userId);
            cookie.setMaxAge(60 * 60 * 10);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            return ResultVOUtil.success("普通账户登录成功");
        }else{
            return ResultVOUtil.error(1, "密码错误");
        }
    }

    @GetMapping("logout")
    public ResultVO logout(HttpServletResponse httpServletResponse){
        Cookie cookie = new Cookie("userId", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return ResultVOUtil.success("登出成功");
    }
}