package xpu.ctrl.docker.entity;

import lombok.Data;

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
}