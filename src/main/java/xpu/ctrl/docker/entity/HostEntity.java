package xpu.ctrl.docker.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * (HostEntity)实体类
 *
 * @author makejava
 * @since 2020-03-12 12:29:30
 */
@Data
@Entity
@DynamicUpdate
public class HostEntity implements Serializable {
    private static final long serialVersionUID = 324943389590812974L;
    @Id
    private String hostIp;
    
    private String hostName;
    
    private Integer hostCpuNumber;
    
    private String hostOs;
    
    private Integer hostStatus;
}