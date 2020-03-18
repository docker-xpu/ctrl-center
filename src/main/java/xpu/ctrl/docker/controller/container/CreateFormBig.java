package xpu.ctrl.docker.controller.container;

import lombok.Data;

import java.util.List;

@Data
public class CreateFormBig {
    /**
     * image_name : nginx
     * container_name : mynginx_04
     * cmd : ["nginx","-g","daemon off;"]
     * volumes : [{"host_volume":"/root/","container_volume":"/root/xxx"}]
     * working_dir :
     * container_port_proto : tcp
     * container_port : 80
     * host_port : 9000
     * cpu_shares : 1024
     * memory : 400000000
     */
    private String ip;
    private String image_name;
    private String container_name;
    private String working_dir;
    private String container_port_proto;
    private String container_port;
    private String host_port;
    private int cpu_shares;
    private int memory;
    private List<String> cmd;
    private List<VolumesBean> volumes;

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getContainer_name() {
        return container_name;
    }

    public void setContainer_name(String container_name) {
        this.container_name = container_name;
    }

    public String getWorking_dir() {
        return working_dir;
    }

    public void setWorking_dir(String working_dir) {
        this.working_dir = working_dir;
    }

    public String getContainer_port_proto() {
        return container_port_proto;
    }

    public void setContainer_port_proto(String container_port_proto) {
        this.container_port_proto = container_port_proto;
    }

    public String getContainer_port() {
        return container_port;
    }

    public void setContainer_port(String container_port) {
        this.container_port = container_port;
    }

    public String getHost_port() {
        return host_port;
    }

    public void setHost_port(String host_port) {
        this.host_port = host_port;
    }

    public int getCpu_shares() {
        return cpu_shares;
    }

    public void setCpu_shares(int cpu_shares) {
        this.cpu_shares = cpu_shares;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public List<String> getCmd() {
        return cmd;
    }

    public void setCmd(List<String> cmd) {
        this.cmd = cmd;
    }

    public List<VolumesBean> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<VolumesBean> volumes) {
        this.volumes = volumes;
    }
    @Data
    public static class VolumesBean {
        /**
         * host_volume : /root/
         * container_volume : /root/xxx
         */

        private String host_volume;
        private String container_volume;

        public String getHost_volume() {
            return host_volume;
        }

        public void setHost_volume(String host_volume) {
            this.host_volume = host_volume;
        }

        public String getContainer_volume() {
            return container_volume;
        }

        public void setContainer_volume(String container_volume) {
            this.container_volume = container_volume;
        }
    }
}
