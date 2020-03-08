package xpu.ctrl.docker.service;

import xpu.ctrl.docker.dataobject.File;
import xpu.ctrl.docker.vo.FileVO;

import java.util.List;
import java.util.Optional;

public interface FileService {
    /** 保存文件 */
    FileVO saveBigFile(File file);

    /** 文件列表 */
    List<FileVO> listBigFiles();

    /** 删除文件 */
    void removeBigFile(String id);

    /** 下载文件 */
    Optional<File> getBigFileById(String id);
}