package xpu.ctrl.docker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xpu.ctrl.docker.entity.CreateArgsForm;

public interface CreateArgsFormRepository extends JpaRepository<CreateArgsForm, String> {
}
