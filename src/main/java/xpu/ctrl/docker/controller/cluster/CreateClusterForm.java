package xpu.ctrl.docker.controller.cluster;

import lombok.Data;

import java.util.List;

@Data
public class CreateClusterForm{

    /**
     * pod_name : AAA
     * image_name : nginx
     * container_num : 3
     * volumes : [{"host_volume":"/root","container_volume":"/root"},{"host_volume":"/root","container_volume":"/root"}]
     * run_command : tar -zxvf && cd /root/
     * container_port : 80
     * host_port : 8085
     * container_port_proto :
     */

    private String pod_name;
    private String image_name;
    private int container_num;
    private List<String> run_command;
    private String container_port;
    private String host_port;
    private String container_port_proto;
    private List<xpu.ctrl.docker.controller.container.CreateForm.VolumesBean> volumes;
}