package xpu.ctrl.docker.dataobject.repository;

import lombok.Data;

import java.util.List;

@Data
public class RepositoryImageInfo {

    /**
     * name : myngx
     * tags : [{"tagName":"0.2","sha256":"sha256:1b750fd04e75f7cda43cebe81b146feeeebcae8787667ac01f97a19d9ed47d11"}]
     */

    private String name;
    private List<TagsBean> tags;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean {
        /**
         * tagName : 0.2
         * sha256 : sha256:1b750fd04e75f7cda43cebe81b146feeeebcae8787667ac01f97a19d9ed47d11
         */
        private String tagName;
        private String sha256;

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getSha256() {
            return sha256;
        }

        public void setSha256(String sha256) {
            this.sha256 = sha256;
        }
    }
}