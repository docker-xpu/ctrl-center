package xpu.ctrl.docker.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class StorageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long createTime;
    private String content;
}
