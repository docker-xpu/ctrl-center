package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.HostEntity;

import java.util.List;

public interface HostEntityRepository extends JpaRepository<HostEntity, String> {
    List<HostEntity> findAllByHostStatus(Integer status);
}
