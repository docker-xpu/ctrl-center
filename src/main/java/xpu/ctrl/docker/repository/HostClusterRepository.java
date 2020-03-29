package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.HostCluster;

import java.util.List;

public interface HostClusterRepository extends JpaRepository<HostCluster, Integer> {
    List<HostCluster> findAllByPodId(String podId);
    List<HostCluster> findAllByPodIdAndIp(String podId, String ip);
    void deleteAllByPodIdAndIp(String podId, String ip);
}
