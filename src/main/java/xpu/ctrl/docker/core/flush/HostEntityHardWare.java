package xpu.ctrl.docker.core.flush;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class HostEntityHardWare implements Serializable {
    private static final long serialVersionUID = -3460450737010584652L;
    @Id
    private Long updateTime;
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