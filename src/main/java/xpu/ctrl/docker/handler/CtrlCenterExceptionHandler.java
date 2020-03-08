package xpu.ctrl.docker.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xpu.ctrl.docker.exception.CtrlCenterException;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

@ControllerAdvice
public class CtrlCenterExceptionHandler {

    /** 处理控制层 or 服务层异常 */
    @ResponseBody
    @ExceptionHandler(value = CtrlCenterException.class)
    public ResultVO handlerCtrlCenterException(CtrlCenterException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}