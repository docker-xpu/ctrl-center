package xpu.ctrl.docker.entity;

import java.io.Serializable;

/**
 * (HostLicense)实体类
 *
 * @author makejava
 * @since 2020-03-08 13:58:11
 */
public class HostLicense implements Serializable {
    private static final long serialVersionUID = 445469023615706778L;
    
    private Integer licenseId;
    
    private String licenseName;
    
    private String licensePasswd;


    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicensePasswd() {
        return licensePasswd;
    }

    public void setLicensePasswd(String licensePasswd) {
        this.licensePasswd = licensePasswd;
    }

}