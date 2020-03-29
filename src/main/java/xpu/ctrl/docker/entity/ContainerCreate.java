package xpu.ctrl.docker.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ContainerCreate {
    @Id
    private String containerName;

    private String createFrom;

    private String hostIp;

    private String clusterId;
}