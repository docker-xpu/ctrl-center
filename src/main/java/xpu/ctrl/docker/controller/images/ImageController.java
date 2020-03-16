package xpu.ctrl.docker.controller.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.dataobject.repository.RepositoryImageInfo;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.IOException;
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
}