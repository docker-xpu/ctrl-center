package xpu.ctrl.docker.vo;

import lombok.Data;

@Data
public class HostEntityVO {
    private String hostName;

    private String hostIp;

    private Integer hostCpuNumber;

    private String hostOs;

    private String hostStatusStr;

    private Integer hostStatus;
}