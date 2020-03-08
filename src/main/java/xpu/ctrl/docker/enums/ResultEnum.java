package xpu.ctrl.docker.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    SUCCESS(0, "成功"),
    UPLOAD_ERROR(1, "上传镜像参数错误"),
    NOT_FIND_FILE(2, "未找到镜像文件");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}