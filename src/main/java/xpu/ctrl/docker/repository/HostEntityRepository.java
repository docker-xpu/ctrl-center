package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.HostEntity;

public interface HostEntityRepository extends JpaRepository<HostEntity, String> {
}
