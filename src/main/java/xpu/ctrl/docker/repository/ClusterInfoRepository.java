package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.ClusterInfo;

public interface ClusterInfoRepository extends JpaRepository<ClusterInfo, String> {

}