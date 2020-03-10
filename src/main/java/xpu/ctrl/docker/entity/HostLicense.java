package xpu.ctrl.docker.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (HostLicense)实体类
 *
 * @author makejava
 * @since 2020-03-08 14:34:07
 */
@Data
public class HostLicense implements Serializable {
    private static final long serialVersionUID = -56933321423587280L;
    
    private Integer licenseId;
    
    private String licenseName;
    
    private String licensePasswd;
}