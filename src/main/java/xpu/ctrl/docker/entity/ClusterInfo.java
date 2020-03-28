package xpu.ctrl.docker.entity;

import lombok.Data;
import xpu.ctrl.docker.enums.RunStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ClusterInfo {
    @Id
    private String id;

    private String podName;

    private Integer nodeNumber;

    private Integer nodePort;

    private String nginxName;

    private Long createTime;

    private Integer status = RunStatusEnum.RUNNING.getCode();
}