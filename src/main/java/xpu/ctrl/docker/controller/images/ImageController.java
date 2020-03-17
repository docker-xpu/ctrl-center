package xpu.ctrl.docker.controller.images;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.dataobject.repository.RepositoryImageInfo;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageServer imageServer;

    @GetMapping("list")
    public ResultVO list(){
        try {
            List<RepositoryImageInfo> infoList = imageServer.getAllImageServer();
            return ResultVOUtil.success(infoList);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVOUtil.success();
        }
    }

    @RequestMapping("delete")
    public ResultVO delete(String name, String sha256){
        String url = String.format("http://139.159.254.242:5000/v2/%s/manifests/%s", name, sha256);
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().delete().url(url).build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if(execute.isSuccessful()) return ResultVOUtil.success();
            return ResultVOUtil.error(3, "删除失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVOUtil.error(2, "删除失败");
        }
    }
}