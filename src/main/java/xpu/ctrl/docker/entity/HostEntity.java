package xpu.ctrl.docker.entity;

import java.io.Serializable;

/**
 * (HostEntity)实体类
 *
 * @author makejava
 * @since 2020-03-08 14:24:19
 */
public class HostEntity implements Serializable {
    private static final long serialVersionUID = -53107980982678477L;
    
    private Integer hostId;
    
    private String hostName;
    
    private String hostIp;
    
    private Integer hostCpuNumber;
    
    private String hostOs;
    
    private Integer hostStatus;


    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Integer getHostCpuNumber() {
        return hostCpuNumber;
    }

    public void setHostCpuNumber(Integer hostCpuNumber) {
        this.hostCpuNumber = hostCpuNumber;
    }

    public String getHostOs() {
        return hostOs;
    }

    public void setHostOs(String hostOs) {
        this.hostOs = hostOs;
    }

    public Integer getHostStatus() {
        return hostStatus;
    }

    public void setHostStatus(Integer hostStatus) {
        this.hostStatus = hostStatus;
    }

}