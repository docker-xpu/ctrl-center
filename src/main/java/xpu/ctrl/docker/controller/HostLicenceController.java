package xpu.ctrl.docker.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("new")
    public ResultVO newHostLicense(String licenseName, String password){
        if(StringUtils.isEmpty(licenseName) || StringUtils.isEmpty(password))
            return ResultVOUtil.error(1, "请检查参数完整性");
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
    @PostMapping("delete")
    public ResultVO deleteHostLicense(@RequestParam("licenseId") Integer licenseId){
        if(licenseId == null){
            return ResultVOUtil.error(1, "凭证删除失败");
        }
        int deleteById = hostLicenseDao.deleteById(licenseId);
        if(deleteById > 0){
            return ResultVOUtil.success();
        }else{
            return ResultVOUtil.error(1, "凭证删除失败");
        }
    }

    // 获取所有凭证
    @GetMapping("all")
    public ResultVO getAllHostLicense(){
        List<HostLicense> hostLicenses = hostLicenseDao.queryAll(new HostLicense());
        return ResultVOUtil.success(hostLicenses);
    }
}