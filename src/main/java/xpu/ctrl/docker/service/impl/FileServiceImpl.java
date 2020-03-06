package xpu.ctrl.docker.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.BsonObjectId;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import xpu.ctrl.docker.dataobject.File;
import xpu.ctrl.docker.repository.FileRepository;
import xpu.ctrl.docker.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import xpu.ctrl.docker.util.MD5Util;

import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    public FileRepository fileRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File saveBigFile(File file) {
        Binary content = file.getContent();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getData());
        ObjectId objectId = gridFsTemplate.store(byteArrayInputStream, file.getName(), file.getContentType());
        String saveResultId = objectId.toString();
        return toFile(Objects.requireNonNull(gridFsTemplate.findOne(query(Criteria.where("_id").is(saveResultId)))));
    }

    @Override
    public void removeFile(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public void removeBigFile(String id) {
        //根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
    }

    @Override
    public Optional<File> getFileById(String id) {
        return fileRepository.findById(id);
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
                String contentType = txtFile.getContentType();
                try {
                    InputStream inputStream = txtFile.getInputStream();
                    file = new File(fileName, contentType, length, new Binary(IOUtils.toByteArray(inputStream)));
                    file.setPath(id);
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
    public List<File> listFilesByPage(int pageIndex, int pageSize) {
        Page<File> page;
        List<File> list;

        Sort sort = new Sort(Direction.DESC,"uploadDate");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        page = fileRepository.findAll(pageable);
        list = page.getContent();
        return list;
    }

    @Override
    public List<File> listBigFiles() {
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
            String contentType = txtFile.getContentType();
//            try {
//                InputStream inputStream = txtFile.getInputStream();
//                byte[] bytes = IOUtils.toByteArray(inputStream);
//                file = new File(fileName, contentType, length, new Binary(bytes));
//                file.setMd5(MD5Util.getMD5(inputStream));
//                file.setPath(fileId);
//                file.setId(fileId);
//                file.setUploadDate(uploadDate);
//                returnList.add(file);
//            } catch (IOException | NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
            file = new File(fileName, contentType, length, null);
            file.setMd5(one.getMD5()); //避免MD5计算导致IO负载过大的问题
            file.setPath(fileId);
            file.setPath(fileId);
            file.setId(fileId);
            file.setUploadDate(uploadDate);
            returnList.add(file);
        }
        return returnList;
    }

    private File toFile(GridFSFile gridFSFile){
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        try {
            byte[] bytes = IOUtils.toByteArray(gridFsResource.getInputStream());
            return new File(gridFSFile.getFilename(), gridFSFile.getMetadata().getString("_contentType"), gridFSFile.getLength(), new Binary(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}