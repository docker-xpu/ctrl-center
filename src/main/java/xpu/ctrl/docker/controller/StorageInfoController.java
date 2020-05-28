package xpu.ctrl.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.entity.StorageInfo;
import xpu.ctrl.docker.repository.StorageInfoRepository;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("storage")
public class StorageInfoController {
    @Autowired
    private StorageInfoRepository storageInfoRepository;

    //Push到Server
    @PostMapping("push")
    public ResultVO storageIn(String storage){
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setCreateTime(System.currentTimeMillis());
        storageInfo.setContent(storage);
        StorageInfo save = storageInfoRepository.save(storageInfo);
        if(save != null)  return ResultVOUtil.success();
        return ResultVOUtil.error(1, "未知错误");
    }

    @GetMapping("get")
    public ResultVO getStorage(Integer id){
        Optional<StorageInfo> byId = storageInfoRepository.findById(id);
        return byId.map(ResultVOUtil::success).orElseGet(() -> ResultVOUtil.error(1, "未知错误"));
    }

    @GetMapping("delete")
    public ResultVO deleteStorage(Integer id){
        storageInfoRepository.deleteById(id);
        return ResultVOUtil.success();
    }
}