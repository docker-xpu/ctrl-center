package xpu.ctrl.docker.controller.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xpu.ctrl.docker.dataobject.ImageFile;
import xpu.ctrl.docker.service.FileService;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private FileService fileService;

    @PostMapping("save")
    public ResultVO saveCodeToFile(CodeForm codeForm){
        String[] codeFormCode = codeForm.getCode();
        List<String> stringList = Arrays.asList(codeFormCode);
        File file = new File("a.txt");
        try {
            FileUtils.writeLines(file, stringList, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Binary binary = new Binary(FileUtils.readFileToByteArray(file));
            ImageFile imageFile = new ImageFile(codeForm.getFilename(), file.length(), binary);
            fileService.saveBigFile(imageFile);
            boolean delete = file.delete();
            log.info("【保存代码删除本地文件结果】delete={}", delete);
            return ResultVOUtil.success();
        } catch (IOException e) {
            log.error(e.toString());
            return ResultVOUtil.error(1, "DEBUG错误-01");
        }
    }
}
