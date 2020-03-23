package xpu.ctrl.docker.controller.files;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xpu.ctrl.docker.dataobject.ImageFile;
import xpu.ctrl.docker.service.FileService;
import xpu.ctrl.docker.util.ResultVOUtil;


import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/sftp")
public class MySFTPController {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private FileService fileService;

    @GetMapping("list")
    public String listFiles(@RequestParam(value = "path", defaultValue = "/") String path, String ip){
        String urlString = String.format("http://%s:8080//api/host/listFiles/?path=%s", ip, path);
        try {
            URI url = new URI(urlString);
            return IOUtils.toString(url, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONObject.toJSONString(ResultVOUtil.error(1, "网络错误"));
        }
    }

    @PostMapping("upload")
    public String uploadToRemoteHost(String ip, String path, String fileId) {
        String url = String.format("http://%s:8080//api/host/uploadFile/", ip);
        Optional<ImageFile> bigFileById = fileService.getBigFileById(fileId);
        if(bigFileById.isPresent()){
            ImageFile imageFile = bigFileById.get();
            OkHttpClient okHttpClient = new OkHttpClient();
            MultipartBody.Builder requestBody = new MultipartBody.Builder();
            requestBody.setType(MultipartBody.FORM);

            RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), imageFile.getContent().getData());
            // 参数分别为 请求key 文件名称 RequestBody
            requestBody.addFormDataPart("file", imageFile.getName(), body);

            //要上传的文字参数
            Map<String, String> map = new HashMap<>();
            map.put("name", imageFile.getName());
            map.put("path", path);
            for (String key : map.keySet()) {
                requestBody.addFormDataPart(key, map.get(key));
            }
            MultipartBody build = requestBody.build();
            try {
                Request request = new Request.Builder().post(build).url(url).build();
                Response execute = okHttpClient.newCall(request).execute();
                if(execute.isSuccessful()){
                    return execute.body().string();
                }
                return JSONObject.toJSONString(ResultVOUtil.error(1, "网络错误"));
            } catch (IOException e) {
                e.printStackTrace();
                return JSONObject.toJSONString(ResultVOUtil.error(2, "网络错误2"));
            }
        }
        return JSONObject.toJSONString(ResultVOUtil.error(3, "文件不存在"));
    }

    @PostMapping("upload-list")
    public String uploadToRemoteListHost(String[] ips, String path, String fileId) {
        log.info("【Args】{}、{}、{}", ips, path, fileId);
        CountDownLatch countDownLatch = new CountDownLatch(ips.length);
        for(String ip: ips){
            cachedThreadPool.execute(()->{
                String url = String.format("http://%s:8080//api/host/uploadFile/", ip);
                Optional<ImageFile> bigFileById = fileService.getBigFileById(fileId);
                if (bigFileById.isPresent()) {
                    ImageFile imageFile = bigFileById.get();
                    OkHttpClient okHttpClient = new OkHttpClient();
                    MultipartBody.Builder requestBody = new MultipartBody.Builder();
                    requestBody.setType(MultipartBody.FORM);

                    RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), imageFile.getContent().getData());
                    // 参数分别为 请求key 文件名称 RequestBody
                    requestBody.addFormDataPart("file", imageFile.getName(), body);

                    //要上传的文字参数
                    Map<String, String> map = new HashMap<>();
                    map.put("name", imageFile.getName());
                    map.put("path", path);
                    for (String key : map.keySet()) {
                        requestBody.addFormDataPart(key, map.get(key));
                    }
                    MultipartBody build = requestBody.build();
                    try {
                        Request request = new Request.Builder().post(build).url(url).build();
                        Response execute = okHttpClient.newCall(request).execute();
                        if (execute.isSuccessful()) {
                            execute.body().string();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println("减一"+ip);
                        countDownLatch.countDown();
                    }
                }else {
                    System.out.println("无文件，减一"+ip);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(ResultVOUtil.success());
    }
}

@Data
class FTPForm{
    String ip;
    String path;
    String fileId;
}