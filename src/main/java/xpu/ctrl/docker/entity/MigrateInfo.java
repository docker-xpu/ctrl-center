package xpu.ctrl.docker.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class MigrateInfo {
    @Id
    private Long migrateId;

    private String migrateLog;
}
