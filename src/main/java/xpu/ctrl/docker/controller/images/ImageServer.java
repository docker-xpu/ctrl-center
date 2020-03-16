package xpu.ctrl.docker.controller.images;

import xpu.ctrl.docker.dataobject.repository.RepositoryImageInfo;

import java.io.IOException;
import java.util.List;

public interface ImageServer {
    List<RepositoryImageInfo> getAllImageServer() throws IOException;
}
