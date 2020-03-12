package xpu.ctrl.docker.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * (HostLicense)实体类
 *
 * @author makejava
 * @since 2020-03-12 12:26:39
 */
@Data
@Entity
@DynamicUpdate
public class HostLicense implements Serializable {
    private static final long serialVersionUID = 379560734367581284L;
    @Id
    private Integer licenseId;
    
    private String licenseName;
    
    private String licensePasswd;
}