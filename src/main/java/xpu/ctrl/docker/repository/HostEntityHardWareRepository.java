package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.core.flush.HostEntityHardWare;

public interface HostEntityHardWareRepository extends JpaRepository<HostEntityHardWare, Long> {
}
