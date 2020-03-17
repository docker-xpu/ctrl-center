package xpu.ctrl.docker.vo;

import lombok.Data;
import xpu.ctrl.docker.dataobject.DksvContainerInfo;

import java.util.List;

@Data
public class HostRunningVO {
    private String timestamp;

    private String host_ip;

    private double cpu;

    private double disk;

    private Io io;

    private double mem;



    @Data
    public static
    class Io {
        private long package_sent;

        private long package_recv;
    }
}

