package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.ClusterInfo;

import java.util.List;

public interface ClusterInfoRepository extends JpaRepository<ClusterInfo, String> {
    List<ClusterInfo> findAllByStatus(Integer status);
}