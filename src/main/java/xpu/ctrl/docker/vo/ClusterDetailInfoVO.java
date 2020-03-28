package xpu.ctrl.docker.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class ClusterDetailInfoVO implements Serializable {
    private static final long serialVersionUID = 458339903353462352L;
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
    private String createTimeStr;
    private Long createTime;
    private int nodeNumber;
    private int nodePort;
    private Integer status;
    private String statusStr;
    private String nginxName;
    private String gateWayIp;
    private AvgLoadBean avgLoad;
    private List<HostsBean> hosts;
    private HostTree hostTree;

    @Data
    public static class AvgLoadBean implements Serializable{
        private static final long serialVersionUID = 3227658250332199832L;
        /**
         * Mem : {"xAxis":["2019-03-26 10:15:30","2019-03-26 10:15:35"],"data":[0.23,0.45]}
         * Cpu : {"xAxis":["2019-03-26 10:15:30","2019-03-26 10:15:35"],"data":[0.23,0.45]}
         */
        private MemBean Mem;
        private CpuBean Cpu;

        @Data
        public static class MemBean {
            private LinkedList<String> xAxis;
            private LinkedList<Double> data;
        }

        @Data
        public static class CpuBean {
            private LinkedList<String> xAxis;
            private LinkedList<Double> data;
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