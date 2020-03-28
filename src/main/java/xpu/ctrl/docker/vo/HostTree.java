package xpu.ctrl.docker.vo;

import lombok.Data;

import java.util.List;

@Data
public class HostTree {
    /**
     * name : clusterName
     * children : [{"name":"主机IP","children":[{"name":"容器名","value":"创建时间"}]}]
     */
    private String name;
    private List<ChildrenBeanX> children;

    @Data
    public static class ChildrenBeanX {
        /**
         * name : 主机IP
         * children : [{"name":"容器名","value":"创建时间"}]
         */
        private String name;
        private List<ChildrenBean> children;

        @Data
        public static class ChildrenBean {
            /**
             * name : 容器名
             * value : 创建时间
             */
            private String name;
            private String value;
        }
    }
}
