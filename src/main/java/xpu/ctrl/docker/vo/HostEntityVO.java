package xpu.ctrl.docker.vo;

import lombok.Data;
import xpu.ctrl.docker.dataobject.DksvContainerInfo;
import xpu.ctrl.docker.dataobject.DksvHostInfo;

import java.math.BigInteger;
import java.util.List;

@Data
public class HostEntityVO {
    private String hostName;

    private String hostIp;

    private BigInteger logical_cores;
    private BigInteger physical_cores;

    private BigInteger disk_total;
    private BigInteger disk_free;

    private BigInteger bootTime;
    private BigInteger upTime;

    private String hostOs;
    private String hostPlatformOs;
    private String hostKernelVersion;
    private BigInteger memTotal;
    private BigInteger memFree;

    private String hostStatusStr;
    private Integer hostStatus;
    private DksvHostInfo.LoadInfoBean loadInfo;

    private List<DksvContainerInfo> containers;
}