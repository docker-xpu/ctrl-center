package xpu.ctrl.docker.entity;

import lombok.Data;
import xpu.ctrl.docker.enums.RunStatusEnum;

import java.io.Serializable;

/**
 * (HostEntity)实体类
 *
 * @author makejava
 * @since 2020-03-08 14:24:19
 */
@Data
public class HostEntity implements Serializable {
    private static final long serialVersionUID = -53107980982678477L;
    
    private Integer hostId;
    
    private String hostName;
    
    private String hostIp;
    
    private Integer hostCpuNumber;
    
    private String hostOs;
    
    private Integer hostStatus = RunStatusEnum.RUNNING.getCode();
}