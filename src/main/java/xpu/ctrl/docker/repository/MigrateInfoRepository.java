package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.MigrateInfo;

public interface MigrateInfoRepository extends JpaRepository<MigrateInfo, Long> {
}
