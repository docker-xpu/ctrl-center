package xpu.ctrl.docker.controller;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xpu.ctrl.docker.dataobject.File;
import xpu.ctrl.docker.enums.ResultEnum;
import xpu.ctrl.docker.exception.CtrlCenterException;
import xpu.ctrl.docker.service.FileService;
import xpu.ctrl.docker.util.MD5Util;
import xpu.ctrl.docker.util.ResultVOUtil;
import xpu.ctrl.docker.vo.FileVO;
import xpu.ctrl.docker.vo.ResultVO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * 镜像上传下载中心
 */
@RestController
@RequestMapping("/images")
public class ImagesController {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private FileService fileService;

    /** 文件列表 */
    @GetMapping("list")
    public ResultVO index() {
        return ResultVOUtil.success(fileService.listBigFiles());
    }

    /** 下载文件 */
    @GetMapping("pull")
    public ResponseEntity<Object> serveFile(String id) {
        Optional<File> file = fileService.getBigFileById(id);
        return file.<ResponseEntity<Object>>map(value -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(
                        value.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_LENGTH, value.getSize() + "").header("Connection", "close")
                .body(value.getContent().getData())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount"));
    }

    /** 上传接口 */
    @PostMapping("upload")
    public ResultVO handleFileUpload(@RequestParam("file") MultipartFile file) {
        FileVO returnFile;
        try {
            File f = new File(file.getOriginalFilename(), file.getSize(), new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            returnFile = fileService.saveBigFile(f);
            return ResultVOUtil.success(String.format("http://%s:%s/images/pull?id=%s", serverAddress, serverPort, returnFile.getId()));
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new CtrlCenterException(ResultEnum.UPLOAD_ERROR);
        }
    }

    /** 删除文件 */
    @GetMapping("delete")
    public ResultVO deleteFile(String id) {
        try {
            fileService.removeBigFile(id);
            return ResultVOUtil.success();
        } catch (Exception e) {
            throw new CtrlCenterException(ResultEnum.NOT_FIND_FILE);
        }
    }
}