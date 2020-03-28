package xpu.ctrl.docker.vo;

import lombok.Data;

@Data
public class ClusterInfoVO {
    private String id;

    private String podName;

    private Integer nodeNumber;

    private Integer nodePort;

    private String nginxName;

    private Long createTime;

    private String createTimeStr;

    private String gateWayIp;

    private Integer status;
    private String statusStr;
}