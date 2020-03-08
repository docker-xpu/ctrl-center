package xpu.ctrl.docker.vo;

import lombok.Data;

@Data
public class FileVO {
    private String id;
    private String name; // 文件名称
    private String sizeStr;
    private String uploadDateStr;
    private String md5;
}