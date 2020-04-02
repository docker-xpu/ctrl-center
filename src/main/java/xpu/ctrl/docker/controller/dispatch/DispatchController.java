package xpu.ctrl.docker.controller.dispatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.entity.MigrateInfo;
import xpu.ctrl.docker.repository.MigrateInfoRepository;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("dispatch")
public class DispatchController {
    @Autowired
    private MigrateInfoRepository migrateInfoRepository;

    //获取变更日志信息
    @GetMapping("log")
    public ResultVO getLog(){
        return ResultVOUtil.success(migrateInfoRepository.findAll());
    }
}
