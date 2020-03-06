package xpu.ctrl.docker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import xpu.ctrl.docker.dataobject.File;

public interface FileRepository extends MongoRepository<File, String> {
}