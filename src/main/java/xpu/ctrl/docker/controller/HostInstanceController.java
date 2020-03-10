package xpu.ctrl.docker.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.core.ssh.SshConnectionPool;
import xpu.ctrl.docker.dao.HostLicenseDao;
import xpu.ctrl.docker.entity.HostLicense;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import javax.annotation.Resource;
import java.io.IOException;

//物理主机
@Slf4j
@RestController
@RequestMapping("/host")
public class HostInstanceController {
    private SshConnectionPool instance = SshConnectionPool.getSSHConnectionPoolInstance();

    @Resource
    private HostLicenseDao hostLicenseDao;

    // 测试SSH连接
    @PostMapping("test-conn")
    public ResultVO addNewHost(String ip, Integer licenseId){
        HostLicense hostLicense = hostLicenseDao.queryById(licenseId);
        if(hostLicense == null) return ResultVOUtil.error(1, "凭据选择错误");
        Connection root = instance.getConnectionByIP(ip, "root", hostLicense.getLicensePasswd());
        Session session;
        try {
            if(root == null) return ResultVOUtil.error(1, "连接失败");
            session = root.openSession();
            session.execCommand("whoami");
            String toString = IOUtils.toString(new StreamGobbler(session.getStdout()));
            session.close();
            if(toString.trim().equals("root")) return ResultVOUtil.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVOUtil.error(1, "连接失败");
    }

    // 初始化主机
    @PostMapping("init")
    public ResultVO initHost(String ip, Integer licenseId){
        return ResultVOUtil.success();
    }

    // 初始化完成会调用此接口
    @RequestMapping("init-notify")
    public ResultVO initHostResult(String ip){
        return ResultVOUtil.success();
    }

    // 主机列表
    @PostMapping("list")
    public ResultVO ListHost(){
        return ResultVOUtil.success();
    }

    // 移除主机
    @PostMapping("remove")
    public ResultVO removeHost(String ip){
        return ResultVOUtil.success();
    }
}