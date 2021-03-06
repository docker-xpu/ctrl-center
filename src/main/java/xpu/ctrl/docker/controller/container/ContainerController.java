package xpu.ctrl.docker.controller.container;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xpu.ctrl.docker.controller.RemoteRepositoryContants;
import xpu.ctrl.docker.entity.ContainerCreate;
import xpu.ctrl.docker.repository.ContainerCreateRepository;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Slf4j
@RestController
@RequestMapping("/container")
public class ContainerController {
    @Autowired
    private ContainerCreateRepository containerCreateRepository;

    @RequestMapping("logs")
    public String getOneContainerLog(String ip, String container_name){
        String url = String.format("http://%s:8080//api/container/logs?container_name=%s", ip, container_name);
        try {
            return IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return JSONObject.toJSONString(ResultVOUtil.error(1, "容器离线"));
        }
    }

    public void createForCluster(@org.springframework.web.bind.annotation.RequestBody CreateFormBig createFormBig, String clusterId){
        String pullUrl = String.format("http://%s:8080//api/image/pull", createFormBig.getIp());
        OkHttpClient okHttpClientPull = new OkHttpClient();
        //String imageName = String.format("%s:5000/%s", RemoteRepositoryContants.REPOSITORY_IP, createFormBig.getCreateForm().getImage_name());
        String imageName = "nginx";
        String pullJsonArgs = String.format("{\"image_name\":\"%s\"}", imageName);
        RequestBody requestBodyPull = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), pullJsonArgs);

        //存储创建容器的表单
        ContainerCreate containerCreate = new ContainerCreate();
        containerCreate.setCreateFrom(JSONObject.toJSONString(createFormBig));
        containerCreate.setContainerName(createFormBig.getIp() + createFormBig.getCreateForm().getContainer_name());
        containerCreate.setHostIp(createFormBig.getIp());
        containerCreate.setClusterId(clusterId);
        containerCreateRepository.save(containerCreate);

        //Request requestPull = new Request.Builder().post(requestBodyPull).url(pullUrl).build();
//        try {
//            Response response = okHttpClientPull.newCall(requestPull).execute();
//            String responseString = response.body().string();
//            Integer status = JSONObject.parseObject(responseString).getInteger("status");
//            log.error("【Not error】responseString={}", responseString);
//            if(!status.equals(0)) return;
//        } catch (IOException e) {
//            e.printStackTrace();
//            JSON.toJSONString(ResultVOUtil.error(-1, "网络错误【拉取镜像】"));
//            return;
//        }

        CreateForm createForm = createFormBig.getCreateForm();
        String ip = createFormBig.getIp();
        createForm.setImage_name("nginx:latest");
        String url = String.format("http://%s:8080//api/container/create/", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        String upFrom = JSONObject.toJSONString(createForm);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), upFrom);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = execute.body();
            String string = responseBody.string();
            log.info("【responseBody】{}", string);
            if(execute.isSuccessful()) return;
        } catch (IOException e) {
            JSONObject.toJSONString(ResultVOUtil.error(1, "网络异常，创建失败"));
            return;
        }
        JSONObject.toJSONString(ResultVOUtil.error(2, "网络异常，创建失败"));
    }

    @RequestMapping("create")
    public String create(@org.springframework.web.bind.annotation.RequestBody CreateFormBig createFormBig){
        String pullUrl = String.format("http://%s:8080//api/image/pull", createFormBig.getIp());
        OkHttpClient okHttpClientPull = new OkHttpClient();
        String imageName = String.format("%s:5000/%s", RemoteRepositoryContants.REPOSITORY_IP, createFormBig.getCreateForm().getImage_name());
        String pullJsonArgs = String.format("{\"image_name\":\"%s\"}", imageName);
        RequestBody requestBodyPull = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), pullJsonArgs);

        //存储创建容器的表单
        ContainerCreate containerCreate = new ContainerCreate();
        containerCreate.setCreateFrom(JSONObject.toJSONString(createFormBig));
        containerCreate.setContainerName(createFormBig.getIp() + createFormBig.getCreateForm().getContainer_name());
        containerCreate.setHostIp(createFormBig.getIp());
        containerCreate.setClusterId(null);
        containerCreateRepository.save(containerCreate);

        Request requestPull = new Request.Builder().post(requestBodyPull).url(pullUrl).build();
        try {
            Response response = okHttpClientPull.newCall(requestPull).execute();
            String responseString = response.body().string();
            Integer status = JSONObject.parseObject(responseString).getInteger("status");
            if(!status.equals(0)) return responseString;
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultVOUtil.error(-1, "网络错误【拉取镜像】"));
        }

        CreateForm createForm = createFormBig.getCreateForm();
        String ip = createFormBig.getIp();
        createForm.setImage_name(imageName);
        String url = String.format("http://%s:8080//api/container/create/", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        String upFrom = JSONObject.toJSONString(createForm);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), upFrom);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = execute.body();
            String string = responseBody.string();
            if(execute.isSuccessful()) return string;
        } catch (IOException e) {
            return JSONObject.toJSONString(ResultVOUtil.error(1, "网络异常，创建失败"));
        }
        return JSONObject.toJSONString(ResultVOUtil.error(2, "网络异常，创建失败"));
    }

    @RequestMapping("start")
    public ResultVO start(String ip, String container_name){
        String url = String.format("http://%s:8080/api/container/start", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        ContainerForm containerForm = new ContainerForm(container_name);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(containerForm));
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if(execute.isSuccessful()) return ResultVOUtil.success();
            return ResultVOUtil.error(1, "启动失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVOUtil.error(2, "启动失败");
        }
    }

    @PostMapping("stop")
    public ResultVO stop(String ip, String container_name){
        String url = String.format("http://%s:8080/api/container/stop", ip);
        ContainerFormStop containerFormStop = new ContainerFormStop(container_name, 2);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(containerFormStop));
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if(execute.isSuccessful()) return ResultVOUtil.success();
            return ResultVOUtil.error(1, "停止失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVOUtil.error(2, "停止失败");
        }
    }

    @PostMapping("delete")
    public ResultVO delete(String ip, String container_name, Boolean bol){
        String url = String.format("http://%s:8080/api/container/remove", ip);
        ContainerFormDelete containerFormDelete = new ContainerFormDelete(container_name, bol);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(containerFormDelete));
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if(execute.isSuccessful()) {
                //containerCreateRepository.deleteById(ip + container_name);
                return ResultVOUtil.success();
            }
            return ResultVOUtil.error(1, "删除失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVOUtil.error(2, "删除失败");
        }
    }
}

@Data
class ContainerForm{
    String container_name;
    public ContainerForm(String container_name) {
        this.container_name = container_name;
    }
}

@Data
class ContainerFormStop{
    String container_name;
    Integer time_out;

    public ContainerFormStop(String container_name, Integer time_out) {
        this.container_name = container_name;
        this.time_out = time_out;
    }
}

@Data
class ContainerFormDelete{
    String container_name;
    Boolean force;

    public ContainerFormDelete(String container_name, Boolean force) {
        this.container_name = container_name;
        this.force = force;
    }
}