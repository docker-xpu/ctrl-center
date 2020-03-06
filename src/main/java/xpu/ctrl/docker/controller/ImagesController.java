package xpu.ctrl.docker.controller;

import org.bson.types.Binary;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xpu.ctrl.docker.dataobject.File;
import xpu.ctrl.docker.service.FileService;
import xpu.ctrl.docker.util.MD5Util;
import xpu.ctrl.docker.vo.FileVO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 镜像上传下载中心
 */
@Controller
@RequestMapping("/images")
public class ImagesController {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private FileService fileService;

    /**
     * 大文件不提供分页查询
     */
    @GetMapping("index")
    public ModelAndView index(Map<String, Object> map) {
        map.put("files", toFileVO(fileService.listBigFiles()));
        return new ModelAndView("image/index", map);
    }

    /**
     * 分页查询文件
     */
    @GetMapping("files/{pageIndex}/{pageSize}")
    public ModelAndView listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize,
                                        Map<String, Object> map) {
        map.put("files", toFileVO(fileService.listFilesByPage(pageIndex, pageSize)));
        return new ModelAndView("image/index", map);
    }

    /**
     * 获取文件片信息
     */
    @GetMapping("files/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {
        //Optional<File> file = fileService.getFileById(id);
        Optional<File> file = fileService.getBigFileById(id);
        return file.<ResponseEntity<Object>>map(value -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(
                        value.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .header(HttpHeaders.CONTENT_LENGTH, value.getSize() + "").header("Connection", "close")
                .body(value.getContent().getData())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount"));
    }
    @GetMapping("upload-page")
    public ModelAndView getUploadPage(){
        return new ModelAndView("/image/upload");
    }

    @PostMapping("html—upload")
    public String handleHTMLFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            //fileService.saveFile(f);
            fileService.saveBigFile(f);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/images/index";
        }
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/images/index";
    }

    /**
     * 上传接口
     */
    @ResponseBody
    @PostMapping("upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        File returnFile;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            //returnFile = fileService.saveFile(f);
            returnFile = fileService.saveBigFile(f);
            String path = "http://" + serverAddress + ":" + serverPort + "/images/view/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @GetMapping("view/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {
        Optional<File> file = fileService.getFileById(id);
        return file.<ResponseEntity<Object>>map(value -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + value.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, value.getContentType())
                .header(HttpHeaders.CONTENT_LENGTH, value.getSize() + "").header("Connection", "close")
                .body(value.getContent().getData())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount"));
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable String id) {
        try {
            //fileService.removeFile(id);
            fileService.removeBigFile(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private List<FileVO> toFileVO(List<File> list){
        List<FileVO> fileVOList = new ArrayList<>();
        for(File file: list){
            fileVOList.add(toVO(file));
        }
        return fileVOList;
    }
    private FileVO toVO(File file){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(file, fileVO);
        fileVO.setSizeStr(String.format("%.4f M", file.getSize()/1024.0/1024.0));
        fileVO.setUploadDateStr(format.format(file.getUploadDate()));
        return fileVO;
    }
}