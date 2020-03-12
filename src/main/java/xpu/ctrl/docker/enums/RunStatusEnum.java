package xpu.ctrl.docker.enums;

import lombok.Getter;

@Getter
public enum  RunStatusEnum implements CodeEnum {
    RUNNING(0, "正在运行"),
    STOP(1, "已停止"),
    PAUSE(2, "暂停");

    private Integer code;
    private String message;

    RunStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
