package xpu.ctrl.docker.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    public String toImage(String image_name, String target_image_name, String ip){
        //"target_image_name": "139.159.254.242:5000/python:0.1" 这里的target_image_name = python:0.1
        //打印参数
        log.info("【参数】{}、{}、{}", image_name, target_image_name, ip);
        String url_tag = String.format("http://%s:8080/api/image/tag/", ip);
        OkHttpClient okHttpClient = new OkHttpClient();
        TagForm tagForm = new TagForm();
        tagForm.setImage_name(image_name);
        tagForm.setTarget_image_name(String.format("%s:5000/%s", RemoteRepositoryContants.REPOSITORY_IP, target_image_name));
        String upFrom = JSONObject.toJSONString(tagForm);
        log.info("【tagForm】"+upFrom);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), upFrom);
        Request request = new Request.Builder().post(requestBody).url(url_tag).build();
        Response execute;
        try {
            execute = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSONString(ResultVOUtil.error(1, "网络错误2"));
        }
        if(execute.isSuccessful()){
            String url_push = String.format("http://%s:8080//api/image/push/", ip);
            PushForm pushForm = new PushForm();
            pushForm.setImage_name(String.format("%s:5000%s", RemoteRepositoryContants.REPOSITORY_IP, target_image_name));
            pushForm.setPath(String.format("%s:5000", RemoteRepositoryContants.REPOSITORY_IP));
            OkHttpClient okHttpClientPush = new OkHttpClient();
            String pushFormString = JSONObject.toJSONString(pushForm);
            log.info("【pushForm】" + pushFormString);
            RequestBody requestBodyPush = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), pushFormString);
            Request requestPush = new Request.Builder().post(requestBodyPush).url(url_push).build();
            Response executePush;
            try {
                executePush = okHttpClientPush.newCall(requestPush).execute();
                if(executePush.isSuccessful()){
                    return executePush.body().string();
                }
                return JSON.toJSONString(ResultVOUtil.error(1, "网络错误3"));
            } catch (IOException e) {
                e.printStackTrace();
                return JSON.toJSONString(ResultVOUtil.error(1, "网络错误4"));
            }
        }
        return JSON.toJSONString(ResultVOUtil.error(1, "网络错误1"));
    }
}

@Data
class TagForm{
    private String image_name;
    private String target_image_name;
}

@Data
class PushForm{
    private String image_name;
    private String path;
}
