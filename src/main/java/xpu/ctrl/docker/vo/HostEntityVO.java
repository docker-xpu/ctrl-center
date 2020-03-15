package xpu.ctrl.docker.vo;

import lombok.Data;

@Data
public class HostEntityVO {
    private String hostName;

    private String hostIp;

    private Long logical_cores;
    private Long physical_cores;

    private Long disk_total;
    private Long disk_free;

    private Long bootTime;
    private Long upTime;

    private String hostOs;
    private String hostPlatformOs;
    private String hostKernelVersion;
    private Long memTotal;
    private Long memFree;

    private String hostStatusStr;

    private Integer hostStatus;
}