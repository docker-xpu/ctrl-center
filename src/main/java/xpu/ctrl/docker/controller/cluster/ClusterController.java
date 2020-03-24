package xpu.ctrl.docker.controller.cluster;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.controller.container.ContainerController;
import xpu.ctrl.docker.controller.container.CreateForm;
import xpu.ctrl.docker.controller.container.CreateFormBig;
import xpu.ctrl.docker.entity.ClusterInfo;
import xpu.ctrl.docker.entity.HostCluster;
import xpu.ctrl.docker.repository.ClusterInfoRepository;
import xpu.ctrl.docker.repository.HostClusterRepository;
import xpu.ctrl.docker.service.HostEntityService;
import xpu.ctrl.docker.util.KeyUtil;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.HostEntityVO;
import xpu.ctrl.docker.vo.ResultVO;

import java.awt.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

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

    @PostMapping("create")
    @Transactional
    public ResultVO createOneCluster(@RequestBody CreateClusterForm createForm) throws Exception{
        log.info("【Args】{}", JSONObject.toJSONString(createForm));
        //0、获取可用主机（按负载排个序）
        List<HostEntityVO> runningHostByLoad = hostEntityService.getRunningHostByLoad(createForm.getContainer_num());

        //1、获得可用端口
        List<Integer> portList;
        HostPort hostPortx = null;
        List<HostPort> hostPortList = Lists.newArrayList();
        for (HostEntityVO hostEntityVO: runningHostByLoad){
            hostPortx = new HostPort();
            URL url = new URL(String.format("http://%s:8080//api/host/ports/", hostEntityVO.getHostIp()));
            PortResult portResult = JSONArray.parseObject(IOUtils.toString(url, StandardCharsets.UTF_8), PortResult.class);
            portList = portResult.getData();
            hostPortx.setPortList(portList);
            hostPortx.setHostEntityVO(hostEntityVO);
            hostPortList.add(hostPortx);
        }

        //1.5 新建集群信息
        ClusterInfo clusterInfo = new ClusterInfo();
        String verifyKey = KeyUtil.genVerifyKey();
        clusterInfo.setId(verifyKey);
        clusterInfo.setPodName(createForm.getPod_name());
        clusterInfo.setNodeNumber(createForm.getContainer_num());
        clusterInfo.setNodePort(Integer.parseInt(createForm.getHost_port()));
        clusterInfoRepository.save(clusterInfo);

        //2、新建容器并保存相关信息
        for(HostPort hostPort: hostPortList){
            HostCluster hostCluster = new HostCluster();
            String hostIp = hostPort.getHostEntityVO().getHostIp();
            Integer port = hostPort.getPortList().get(0);
            hostCluster.setIp(hostIp);
            hostCluster.setPort(port);
            hostCluster.setPodId(verifyKey);
            hostClusterRepository.save(hostCluster);

            //开始新建容器
            CreateForm form = new CreateForm();
            form.setVolumes(createForm.getVolumes());
            form.setCmd(createForm.getRun_command());
            String string = Arrays.toString(hostIp.split("."));
            log.error("【容器名称】{}", clusterInfo.getPodName() + string.substring(1, string.length() - 1));
            form.setContainer_name(clusterInfo.getPodName() + string.substring(1, string.length() - 1));
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
            containerController.create(createFormBig);
        }
        //2.5 、启动全部容器
        //3、在负载均衡机上跑一个负载均衡的TCP Nginx

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

class PortResult{
    /**
     * status : 0
     * msg : success
     * data : [10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061,10062,10063,10064,10065,10066,10067,10068,10069,10070,10071,10072,10073,10074,10075,10076,10077,10078,10079,10080,10081,10082,10083,10084,10085,10086,10087,10088,10089,10090,10091,10092,10093,10094,10095,10096,10097,10098,10099]
     */
    private int status;
    private String msg;
    private List<Integer> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
