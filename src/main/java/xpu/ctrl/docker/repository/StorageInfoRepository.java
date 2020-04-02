package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.StorageInfo;

public interface StorageInfoRepository extends JpaRepository<StorageInfo, Integer> {
}