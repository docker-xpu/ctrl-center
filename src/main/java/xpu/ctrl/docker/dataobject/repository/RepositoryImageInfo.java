package xpu.ctrl.docker.dataobject.repository;

import lombok.Data;

import java.util.List;

@Data
public class RepositoryImageInfo {

    /**
     * name : x
     * tags : ["0.1","0.2"]
     */

    private String name;
    private List<String> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
