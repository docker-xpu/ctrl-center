package xpu.ctrl.docker.controller.cluster;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.controller.container.ContainerController;
import xpu.ctrl.docker.controller.container.CreateForm;
import xpu.ctrl.docker.controller.container.CreateFormBig;
import xpu.ctrl.docker.core.ssh.DestHost;
import xpu.ctrl.docker.core.ssh.SSHUtils;
import xpu.ctrl.docker.entity.ClusterInfo;
import xpu.ctrl.docker.entity.HostCluster;
import xpu.ctrl.docker.repository.ClusterInfoRepository;
import xpu.ctrl.docker.repository.HostClusterRepository;
import xpu.ctrl.docker.service.HostEntityService;
import xpu.ctrl.docker.util.KeyUtil;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.HostEntityVO;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 负载最小的主机作为负载均衡的主机
 */
@Slf4j
@RestController
@RequestMapping("/cluster")
public class ClusterController {
    @Autowired
    private ClusterInfoRepository clusterInfoRepository;

    @Autowired
    private ContainerController containerController;

    @Autowired
    private HostClusterRepository hostClusterRepository;

    @Autowired
    private HostEntityService hostEntityService;

    @PostMapping("list")
    public ResultVO list(){
        return ResultVOUtil.success(clusterInfoRepository.findAll());
    }

    @PostMapping("remove")
    public ResultVO remove(String cluster){
        //先把节点删除
        List<HostCluster> allByPodId = hostClusterRepository.findAllByPodId(cluster);
        for(HostCluster hostCluster: allByPodId){
            String containerName = hostCluster.getContainerName();
            String clusterIp = hostCluster.getIp();
            containerController.delete(clusterIp, containerName, true);
            hostClusterRepository.deleteById(hostCluster.getId());
        }

        //删除代理节点
        Optional<ClusterInfo> clusterInfoOptional = clusterInfoRepository.findById(cluster);
        if(clusterInfoOptional.isPresent()){
            ClusterInfo clusterInfo = clusterInfoOptional.get();
            String nginxName = clusterInfo.getNginxName();
            containerController.delete("192.168.2.2", nginxName, true);
            clusterInfoRepository.deleteById(clusterInfo.getId());
        }
        return ResultVOUtil.success();
    }

    @PostMapping("create")
    public ResultVO createOneCluster(@RequestBody CreateClusterForm createForm) throws Exception{
        log.info("【Args】{}", JSONObject.toJSONString(createForm));
        //0、获取可用主机（按负载排个序）
        List<HostEntityVO> runningHostByLoad = hostEntityService.getRunningHostByLoad(createForm.getContainer_num());

        //1、获得可用端口
        List<Integer> portList;
        HostPort hostPortInfo;
        List<HostPort> hostPortList = Lists.newArrayList();
        for (HostEntityVO hostEntityVO: runningHostByLoad){
            hostPortInfo = new HostPort();
            URL url = new URL(String.format("http://%s:8080//api/host/ports/", hostEntityVO.getHostIp()));
            PortResult portResult = JSONArray.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8), PortResult.class);
            portList = portResult.getData();
            hostPortInfo.setPortList(portList);
            hostPortInfo.setHostEntityVO(hostEntityVO);
            hostPortList.add(hostPortInfo);
        }

        //1.5 新建集群信息
        ClusterInfo clusterInfo = new ClusterInfo();
        String verifyKey = KeyUtil.genVerifyKey();
        clusterInfo.setId(verifyKey);
        clusterInfo.setPodName(createForm.getPod_name());
        clusterInfo.setNodeNumber(createForm.getContainer_num());
        clusterInfo.setNodePort(Integer.parseInt(createForm.getHost_port()));

        int containerNum = 0;

        //同一个机子上多个容器
        for (int i = 0; i < 3; i++) {
            //2、新建容器并保存相关信息
            for(HostPort hostPort: hostPortList){
                if(containerNum >= createForm.getContainer_num()) {
                    break;
                }
                HostCluster hostCluster = new HostCluster();
                String hostIp = hostPort.getHostEntityVO().getHostIp();
                Integer port = hostPort.getPortList().get(i);
                hostCluster.setIp(hostIp);
                hostCluster.setPort(port);
                hostCluster.setPodId(verifyKey);

                //开始新建容器
                CreateForm form = new CreateForm();
                form.setVolumes(createForm.getVolumes());
                form.setCmd(createForm.getRun_command());
                String containerName = clusterInfo.getPodName() + "." + i + "." + hostIp;
                log.error("【容器名称】{}", containerName);
                form.setContainer_name(containerName);
                form.setContainer_port_proto(createForm.getContainer_port_proto());
                form.setContainer_port(createForm.getContainer_port());
                form.setHost_port(port+"");
                form.setCpu_shares(1024);
                form.setMemory(40000000);
                form.setImage_name(createForm.getImage_name());
                form.setWorking_dir("/root");

                CreateFormBig createFormBig = new CreateFormBig();
                createFormBig.setIp(hostIp);
                createFormBig.setCreateForm(form);
                //创建容器
                containerController.create(createFormBig);
                containerNum++;
                //启动全部容器
                containerController.start(hostIp, containerName);

                //保存容器名称
                hostCluster.setContainerName(containerName);
                hostClusterRepository.save(hostCluster);
            }
        }

        //3 在负载均衡机上跑一个负载均衡容器
        //3.1 生成负载均衡配置文件
        String initConfigString = NginxConfigContent.content;
        List<HostCluster> hostClusters = hostClusterRepository.findAllByPodId(verifyKey);
        StringBuilder builder = new StringBuilder();
        //server 192.168.2.4:10000;
        for(HostCluster hostCluster: hostClusters){
            String clusterIp = hostCluster.getIp();
            Integer port = hostCluster.getPort();
            builder.append("server ").append(clusterIp).append(":").append(port).append(";\n");
        }
        initConfigString = initConfigString.replace("my_server_ip_and_port_list", builder.toString());
        //initConfigString = initConfigString.replace("my_self_server_port", createForm.getHost_port());
        File nginxConfigFile = new File("conf.tmp");
        String targetFileName = verifyKey+".nginx.conf";

        FileUtils.writeStringToFile(nginxConfigFile, initConfigString);

        log.info("【配置文件内容】{}", FileUtils.readFileToString(nginxConfigFile));

        //3.2 分发配置文件
        //发给谁? 192.168.2.2
        String gateWayHostIp = "192.168.2.2";
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"),
                FileUtils.readFileToByteArray(nginxConfigFile));
        // 参数分别为 请求key 文件名称 RequestBody
        requestBody.addFormDataPart("file", targetFileName, body);

        //要上传的文字参数
        Map<String, String> map = new HashMap<>();
        map.put("name", targetFileName);
        map.put("path", "/root");
        for (String key : map.keySet()) {
            requestBody.addFormDataPart(key, map.get(key));
        }
        MultipartBody build = requestBody.build();
        try {
            String url = String.format("http://%s:8080//api/host/uploadFile/", gateWayHostIp);
            Request request = new Request.Builder().post(build).url(url).build();
            Response execute = okHttpClient.newCall(request).execute();
            if(execute.isSuccessful()){
                log.info("【配置文件发送成功】");
                DestHost destHost = new DestHost(gateWayHostIp, "root", "123456");
                SSHUtils.execCommandSvirt_sandbox_file_t(SSHUtils.getJSchSession(destHost), "/root/"+targetFileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3.3 创建负载均衡容器
        CreateForm form = new CreateForm();
        List<CreateForm.VolumesBean> volumes = createForm.getVolumes();
        CreateForm.VolumesBean volumesBean = new CreateForm.VolumesBean();
        volumesBean.setContainer_volume("/etc/nginx/nginx.conf");
        volumesBean.setHost_volume("/root/" + targetFileName);
        volumes.add(volumesBean);
        form.setVolumes(volumes);

        form.setCmd(Arrays.asList("nginx", "-g", "daemon off;"));
        String containerName = clusterInfo.getPodName() + ".master." + gateWayHostIp;
        log.error("【容器名称】{}", containerName);
        form.setContainer_name(containerName);
        form.setContainer_port_proto("tcp");
        form.setContainer_port("80");
        form.setHost_port(createForm.getHost_port());
        form.setCpu_shares(1024);
        form.setMemory(40000000);
        form.setImage_name("nginx");
        form.setWorking_dir("/root");

        CreateFormBig createFormBig = new CreateFormBig();
        createFormBig.setIp(gateWayHostIp);
        createFormBig.setCreateForm(form);
        CreateFormBig gateWayHostCreateFormBig = new CreateFormBig();
        gateWayHostCreateFormBig.setIp(gateWayHostIp);
        gateWayHostCreateFormBig.setCreateForm(form);

        //创建Master容器
        containerController.create(createFormBig);
        log.info("【创建Master容器Success】");

        clusterInfo.setNginxName(containerName);
        clusterInfoRepository.save(clusterInfo);

        //启动Master容器
        containerController.start(gateWayHostIp, containerName);
        if(nginxConfigFile.delete()) log.info("【临时配置文件已删除】");
        return ResultVOUtil.success();
    }
}

@Data
class HostPort{
    HostEntityVO hostEntityVO;
    List<Integer> portList;
}


@Data
class CreateClusterForm{

    /**
     * pod_name : AAA
     * image_name : nginx
     * container_num : 3
     * volumes : [{"host_volume":"/root","container_volume":"/root"},{"host_volume":"/root","container_volume":"/root"}]
     * run_command : tar -zxvf && cd /root/
     * container_port : 80
     * host_port : 8085
     * container_port_proto :
     */

    private String pod_name;
    private String image_name;
    private int container_num;
    private List<String> run_command;
    private String container_port;
    private String host_port;
    private String container_port_proto;
    private List<xpu.ctrl.docker.controller.container.CreateForm.VolumesBean> volumes;
}

@Data
class PortResult{
    /**
     * status : 0
     * msg : success
     * data : [10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061,10062,10063,10064,10065,10066,10067,10068,10069,10070,10071,10072,10073,10074,10075,10076,10077,10078,10079,10080,10081,10082,10083,10084,10085,10086,10087,10088,10089,10090,10091,10092,10093,10094,10095,10096,10097,10098,10099]
     */
    private int status;
    private String msg;
    private List<Integer> data;
}
