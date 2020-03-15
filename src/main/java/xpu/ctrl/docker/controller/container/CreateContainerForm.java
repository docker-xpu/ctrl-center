package xpu.ctrl.docker.controller.container;

import lombok.Data;

@Data
public class CreateContainerForm {
    private String image_name;
    private String container_name;
    private volumes volumes;
    private String working_dir;
    private String container_port_proto;
    private String container_port;
    private String host_port;
    private Integer cpu_shares;
    private String memory;
}

@Data
class volumes{
    private String host_volume;
    private String container_volume;
}
