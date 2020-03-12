package xpu.ctrl.docker.core.flush;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class HostEntityHardWare {
    @Id
    private String ip;
    private String cpuInfo;
    private String diskInfo;
    private String hostInfo;
    private String ioCounters;
    private String loadInfo;
    private String memInfo;
    private String process;
    private String protoCounters;
    private String sensors;
}