package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.HostCluster;

public interface HostClusterRepository extends JpaRepository<HostCluster, Integer> {
}
