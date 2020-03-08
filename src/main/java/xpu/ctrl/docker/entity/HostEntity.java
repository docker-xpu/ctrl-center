package xpu.ctrl.docker.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (HostEntity)实体类
 *
 * @author makejava
 * @since 2020-03-08 13:50:22
 */
@Data
public class HostEntity implements Serializable {
    private static final long serialVersionUID = 483786541240550831L;
    
    private Integer hostId;
    
    private String hostName;
    
    private String hostIp;
    
    private Integer hostCpuNumber;
    
    private String hostOs;
    
    private Object hostStatus;
}