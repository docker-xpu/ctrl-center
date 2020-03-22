package xpu.ctrl.docker.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.util.ResultVOUtil;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/change")
public class ContainerToImageController {
    @PostMapping("to-image")
    public String toImage(String container_name, String target_image_name, String target_image_tag, String ip){
        //log.info("【接收参数】{}、{}、{}、{}", container_name, target_image_name, target_image_tag, ip);
        String urlCommit = String.format("http://%s:8080//api/container/commit/", ip);
        CommitForm commitForm = new CommitForm(container_name, target_image_name);
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(commitForm));
        Request request = new Request.Builder().post(requestBody).url(urlCommit).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            String executeBody = execute.body().string();
            if(JSONObject.parseObject(executeBody).getInteger("status").equals(0)){
                log.info("【Step one success】");
                String urlTag = String.format("http://%s:8080/api/image/tag/", ip);
                TagForm tagForm = new TagForm(target_image_name + ":latest", String.format("%s:5000/%s:%s", RemoteRepositoryContants.REPOSITORY_IP, target_image_name, target_image_tag));
                requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(tagForm));
                request = new Request.Builder().post(requestBody).url(urlTag).build();
                execute = okHttpClient.newCall(request).execute();
                String executeBodyTwo = execute.body().string();
                if(JSONObject.parseObject(executeBodyTwo).getInteger("status").equals(0)){
                    log.info("【Step two success】");
                    String urlPush = String.format("http://%s:8080/api/image/push/", ip);
                    PushForm pushForm = new PushForm(tagForm.getTarget_image_name(), RemoteRepositoryContants.REPOSITORY_IP);
                    requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject.toJSONString(pushForm));
                    request = new Request.Builder().post(requestBody).url(urlPush).build();
                    execute = okHttpClient.newCall(request).execute();
                    log.info("【Step three success】");
                    return execute.body().string();
                }
                return executeBodyTwo;
            }
            return executeBody;
        } catch (IOException e) {
            return JSON.toJSONString(ResultVOUtil.error(1, "Network Error !!!"));
        }
    }
}

@Data
@AllArgsConstructor
class CommitForm{
    private String container_name;
    private String ref;
}

@Data
@AllArgsConstructor
class TagForm{
    private String image_name;
    private String target_image_name;
}

@Data
@AllArgsConstructor
class PushForm{
    private String image_name;
    private String path;
}