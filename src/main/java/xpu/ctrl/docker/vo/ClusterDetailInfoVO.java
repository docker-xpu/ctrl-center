package xpu.ctrl.docker.vo;

import lombok.Data;

import java.util.List;

@Data
public class ClusterDetailInfoVO {
    /**
     * id : 178211
     * podName : mynginxpod
     * createTime : 1893080389120
     * nodeNumber : 5
     * nodePort : 8024
     * nginxName : blog-1.master.192.168.2.2
     * gateWayIp : 192.168.2.2
     * hosts : [{"ip":"192.168.2.4","containers":["conatinerName1","conatinerName2"]},{"ip":"192.168.2.6","containers":["conatinerName1","conatinerName2","conatinerName3"]}]
     * avgLoad : {"Mem":{"xAxis":["2019-03-26 10:15:30","2019-03-26 10:15:35"],"data":[0.23,0.45]},"Cpu":{"xAxis":["2019-03-26 10:15:30","2019-03-26 10:15:35"],"data":[0.23,0.45]}}
     */

    private String id;
    private String podName;
    private String createTime;
    private int nodeNumber;
    private int nodePort;
    private String nginxName;
    private String gateWayIp;
    private AvgLoadBean avgLoad;
    private List<HostsBean> hosts;

    @Data
    public static class AvgLoadBean {
        /**
         * Mem : {"xAxis":["2019-03-26 10:15:30","2019-03-26 10:15:35"],"data":[0.23,0.45]}
         * Cpu : {"xAxis":["2019-03-26 10:15:30","2019-03-26 10:15:35"],"data":[0.23,0.45]}
         */
        private MemBean Mem;
        private CpuBean Cpu;

        @Data
        public static class MemBean {
            private List<String> xAxis;
            private List<Double> data;
        }

        @Data
        public static class CpuBean {
            private List<String> xAxis;
            private List<Double> data;
        }
    }

    @Data
    public static class HostsBean {
        /**
         * ip : 192.168.2.4
         * containers : ["conatinerName1","conatinerName2"]
         */
        private String ip;
        private List<String> containers;
    }
}