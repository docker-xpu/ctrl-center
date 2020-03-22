package xpu.ctrl.docker.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.util.ResultVOUtil;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/network")
public class NetworkController {
    @GetMapping("list")
    public String getNetWork(String ip){
        if(StringUtils.isEmpty(ip)) return JSONObject.toJSONString(ResultVOUtil.error(-1, "Args Error!"));
        String urlString = String.format("http://%s:8080//api/network/list/", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(urlString).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            return execute.body().string();
        } catch (Exception e) {
            return JSONObject.toJSONString(ResultVOUtil.error(1, "Network Error"));
        }
    }

    @PostMapping("create")
    public String createNetwork(String name, String ip){
        String url = String.format("http://%s:8080//api/network/create/", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        String nameJson = String.format("{\"name\":\"%s\"}", name);
        log.info("【nameJson】{}", nameJson);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), nameJson);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            return execute.body().string();
        } catch (IOException e) {
            return JSONObject.toJSONString(ResultVOUtil.error(1, "Network Error"));
        }
    }

    @PostMapping("delete")
    public String delete(String name, String ip){
        String url = String.format("http://%s:8080/api/network/remove/", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        String nameJson = String.format("{\"name\":\"%s\"}", name);
        log.info("【nameJson】{}", nameJson);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), nameJson);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            return execute.body().string();
        } catch (IOException e) {
            return JSONObject.toJSONString(ResultVOUtil.error(1, "Network Error"));
        }
    }
}