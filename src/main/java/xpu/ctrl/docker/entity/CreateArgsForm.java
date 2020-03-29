package xpu.ctrl.docker.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class CreateArgsForm {
    @Id
    private String cluster_id;
    private String create_args;
}