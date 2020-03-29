package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.ContainerCreate;

import java.util.List;

public interface ContainerCreateRepository extends JpaRepository<ContainerCreate, String> {
    List<ContainerCreate> findAllByHostIp(String hostIp);
}