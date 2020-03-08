package xpu.ctrl.docker.exception;

import lombok.Getter;
import xpu.ctrl.docker.enums.ResultEnum;

@Getter
public class CtrlCenterException extends RuntimeException{
    private Integer code;

    public CtrlCenterException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CtrlCenterException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}