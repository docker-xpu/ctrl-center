package xpu.ctrl.docker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.dao.HostLicenseDao;
import xpu.ctrl.docker.entity.HostLicense;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import javax.annotation.Resource;
import java.util.List;

//物理主机
@RestController
@RequestMapping("/licence")
public class HostLicenceController {
    @Resource
    private HostLicenseDao hostLicenseDao;

    // 添加一个凭证
    @RequestMapping("new")
    public ResultVO newHostLicense(String licenseName, String password){
        HostLicense hostLicense = new HostLicense();
        hostLicense.setLicenseName(licenseName);
        hostLicense.setLicensePasswd(password);
        int insert = hostLicenseDao.insert(hostLicense);
        if(insert > 0){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(1, "凭证添加失败");
        }
    }

    // 删除凭证
    @RequestMapping("delete")
    public ResultVO deleteHostLicense(Integer licenseId){
        int deleteById = hostLicenseDao.deleteById(licenseId);
        if(deleteById > 0){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(1, "凭证删除失败");
        }
    }

    // 获取所有凭证
    @RequestMapping("all")
    public ResultVO getAllHostLicense(){
        List<HostLicense> hostLicenses = hostLicenseDao.queryAll(new HostLicense());
        return ResultVOUtil.success(hostLicenses);
    }
}