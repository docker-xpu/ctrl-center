package xpu.ctrl.docker.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xpu.ctrl.docker.core.ssh.DestHost;
import xpu.ctrl.docker.core.ssh.SSHUtils;
import xpu.ctrl.docker.core.ssh.SshConnectionPool;
import xpu.ctrl.docker.dao.HostLicenseDao;
import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.entity.HostLicense;
import xpu.ctrl.docker.enums.RunStatusEnum;
import xpu.ctrl.docker.repository.HostEntityRepository;
import xpu.ctrl.docker.service.HostEntityService;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.HostEntityVO;
import xpu.ctrl.docker.vo.ResultVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

//物理主机
@Slf4j
@RestController
@RequestMapping("/host")
public class HostInstanceController {
    private SshConnectionPool instance = SshConnectionPool.getSSHConnectionPoolInstance();

    @Resource
    private HostLicenseDao hostLicenseDao;

    @Autowired
    private HostEntityService hostEntityService;

    @Autowired
    private HostEntityRepository hostEntityRepository;

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
    public ResultVO initHost(String ip, Integer licenseId, String version){
        HostLicense hostLicense = hostLicenseDao.queryById(licenseId);
        if(hostLicense == null) return ResultVOUtil.error(1, "凭据选择错误");
        DestHost destHost = new DestHost(ip, "root", hostLicense.getLicensePasswd());
        try {
            SSHUtils.execCommandByShellDeploy(SSHUtils.getJSchSession(destHost));
            TimeUnit.SECONDS.sleep(5);
            SSHUtils.execCommandByShell(SSHUtils.getJSchSession(destHost), ip, version);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success();
    }

    // 初始化完成会调用此接口
    @RequestMapping("init-notify")
    public ResultVO initHostResult(String ip){
        log.info("【主机初始化成功】 {}", ip);
        //保存主机信息
        HostEntity hostEntity = new HostEntity();
        hostEntity.setHostIp(ip);
        hostEntity.setHostName(String.format("IP为%s的主机", ip).trim());
        hostEntity.setHostStatus(RunStatusEnum.RUNNING.getCode());
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String jsonString = IOUtils.toString(new URL(String.format("http://%s:8080/api/host/info", ip)), StandardCharsets.UTF_8);
                JSONObject parseObject = JSONObject.parseObject(jsonString);
                Integer cpuNumber = Integer.parseInt(parseObject.getJSONObject("data").getJSONObject("cpu_info").getString("physical_cores"));
                hostEntity.setHostCpuNumber(cpuNumber);
                hostEntity.setHostOs(parseObject.getJSONObject("data").getJSONObject("host_info").getString("os"));
                hostEntityRepository.save(hostEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return ResultVOUtil.success();
    }

    // 主机列表
    @GetMapping("list")
    public ResultVO ListHost(){
        List<HostEntityVO> hostEntityVOList = hostEntityService.getAllHost();
        return ResultVOUtil.success(hostEntityVOList);
    }

    // 移除主机
    @PostMapping("remove")
    public ResultVO removeHost(String ip){
        //DestHost destHost = new DestHost(ip, "root", hostLicense.getLicensePasswd());
        hostEntityRepository.deleteById(ip);
        //SSHUtils.execCommandByShellDeleteHost()
        return ResultVOUtil.success();
    }

    //获取主机信息
    @RequestMapping("push-info")
    public ResultVO pushHostInfo(@RequestBody JSONObject jsonObject){
        jsonObject.getString("");
        return ResultVOUtil.success();
    }
}