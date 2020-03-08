package xpu.ctrl.docker.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.BsonObjectId;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.dataobject.File;
import xpu.ctrl.docker.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import xpu.ctrl.docker.util.MD5Util;
import xpu.ctrl.docker.vo.FileVO;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Override
    public FileVO saveBigFile(File file) {
        Binary content = file.getContent();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getData());
        ObjectId objectId = gridFsTemplate.store(byteArrayInputStream, file.getName());
        String saveResultId = objectId.toString();
        GridFSFile one = gridFsTemplate.findOne(query(Criteria.where("_id").is(saveResultId)));
        File toFile = toFile(one);
        toFile.setId(saveResultId);
        toFile.setMd5(one.getMD5());
        return toVO(toFile);
    }

    @Override
    public void removeBigFile(String id) {
        //根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
    }

    @Override
    public Optional<File> getBigFileById(String id) {
        GridFsResource[] txtFiles = gridFsTemplate.getResources("*");
        File file;
        for (GridFsResource txtFile : txtFiles) {
            BsonObjectId bsonObjectId = (BsonObjectId) txtFile.getId();
            String fileId = bsonObjectId.getValue().toString();
            if(fileId.equals(id)){
                GridFSFile one = gridFsTemplate.findOne(query(Criteria.where("_id").is(bsonObjectId.getValue())));
                String fileName = one.getFilename();
                Date uploadDate = one.getUploadDate();
                long length = one.getLength();//字节数
                try {
                    InputStream inputStream = txtFile.getInputStream();
                    file = new File(fileName, length, new Binary(IOUtils.toByteArray(inputStream)));
                    file.setMd5(MD5Util.getMD5(inputStream));
                    file.setUploadDate(uploadDate);
                    file.setId(id);
                    return Optional.of(file);
                } catch (IOException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<FileVO> listBigFiles() {
        //获取所有文件
        List<File> returnList = new ArrayList<>();
        GridFsResource[] txtFiles = gridFsTemplate.getResources("*");
        File file;
        for (GridFsResource txtFile : txtFiles) {
            BsonObjectId bsonObjectId = (BsonObjectId) txtFile.getId();
            String fileId = bsonObjectId.getValue().toString();
            GridFSFile one = gridFsTemplate.findOne(query(Criteria.where("_id").is(bsonObjectId.getValue())));
            assert one != null;
            String fileName = one.getFilename();
            Date uploadDate = one.getUploadDate();
            long length = one.getLength();//字节数

            file = new File(fileName, length, null);
            file.setMd5(one.getMD5()); //避免MD5计算导致IO负载过大的问题
            file.setId(fileId);
            file.setUploadDate(uploadDate);
            returnList.add(file);
        }
        return toFileVO(returnList);
    }

    private File toFile(GridFSFile gridFSFile){
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        try {
            byte[] bytes = IOUtils.toByteArray(gridFsResource.getInputStream());
            return new File(gridFSFile.getFilename(), gridFSFile.getLength(), new Binary(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        fileVO.setSizeStr(String.format("%.2f M", file.getSize()/1024.0/1024.0));
        fileVO.setUploadDateStr(format.format(file.getUploadDate()));
        return fileVO;
    }
}