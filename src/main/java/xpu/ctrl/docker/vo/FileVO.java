package xpu.ctrl.docker.vo;

import lombok.Data;

@Data
public class FileVO {
    private String id;
    private String name; // 文件名称
    private String contentType; // 文件类型
    private String sizeStr;
    private String uploadDateStr;
    private String md5;
    private String path; // 文件路径
}
