package xpu.ctrl.docker.service;

import xpu.ctrl.docker.dataobject.File;

import java.util.List;
import java.util.Optional;

public interface FileService {
    /**
     * 保存文件
     * @param File
     * @return
     */
    File saveFile(File file);
    File saveBigFile(File file);

    /**
     * 删除文件
     * @param File
     * @return
     */
    void removeFile(String id);

    /**
     * 根据id获取文件
     * @param File
     * @return
     */
    Optional<File> getFileById(String id);

    /**
     * 分页查询，按上传时间降序
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<File> listFilesByPage(int pageIndex, int pageSize);

    List<File> listBigFiles();

    void removeBigFile(String id);

    Optional<File> getBigFileById(String id);
}
