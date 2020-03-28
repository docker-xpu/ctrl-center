package xpu.ctrl.docker.controller.cluster.impl;

import lombok.Data;

import java.util.List;

@Data
public class ContainerStatus {

    /**
     * blkio_stats : {}
     * cpu_stats : {"cpu_usage":{"percpu_usage":[13859735,18729501,3726455,264890],"total_usage":36580581,"usage_in_kernelmode":20000000,"usage_in_usermode":10000000},"online_cpus":0,"system_cpu_usage":792718620000000,"throttling_data":{"periods":0,"throttled_periods":0,"throttled_time":0}}
     * memory_stats : {"failcnt":0,"limit":39997440,"max_usage":1576960,"stats":{"active_anon":1437696,"active_file":0,"cache":12288,"hierarchical_memory_limit":39997440,"inactive_anon":4096,"inactive_file":8192,"mapped_file":4096,"pgfault":1541,"pgmajfault":0,"pgpgin":643,"pgpgout":289,"rss":1437696,"rss_huge":0,"total_active_anon":1437696,"total_active_file":0,"total_cache":12288,"total_inactive_anon":4096,"total_inactive_file":8192,"total_mapped_file":4096,"total_pgfault":1541,"total_pgmajfault":0,"total_pgpgin":643,"total_pgpgout":289,"total_rss":1437696,"total_rss_huge":0,"total_unevictable":0,"total_writeback":0,"unevictable":0,"writeback":0},"usage":1449984}
     * networks : {"eth0":{"rx_bytes":3000,"rx_dropped":0,"rx_errors":0,"rx_packets":48,"tx_bytes":656,"tx_dropped":0,"tx_errors":0,"tx_packets":8},"eth5":{"rx_bytes":0,"rx_dropped":0,"rx_errors":0,"rx_packets":0,"tx_bytes":0,"tx_dropped":0,"tx_errors":0,"tx_packets":0}}
     * pids_stats : {"current":2}
     * precpu_stats : {"cpu_usage":{"percpu_usage":[13859735,18729501,3726455,264890],"total_usage":36580581,"usage_in_kernelmode":20000000,"usage_in_usermode":10000000},"online_cpus":0,"system_cpu_usage":792714640000000,"throttling_data":{"periods":0,"throttled_periods":0,"throttled_time":0}}
     * read : 2020-03-26T11:28:24.025400435Z
     */

    private BlkioStatsBean blkio_stats;
    private CpuStatsBean cpu_stats;
    private MemoryStatsBean memory_stats;
    private NetworksBean networks;
    private PidsStatsBean pids_stats;
    private PrecpuStatsBean precpu_stats;
    private String read;

    @Data
    public static class BlkioStatsBean {
    }

    @Data
    public static class CpuStatsBean {
        /**
         * cpu_usage : {"percpu_usage":[13859735,18729501,3726455,264890],"total_usage":36580581,"usage_in_kernelmode":20000000,"usage_in_usermode":10000000}
         * online_cpus : 0
         * system_cpu_usage : 792718620000000
         * throttling_data : {"periods":0,"throttled_periods":0,"throttled_time":0}
         */

        private CpuUsageBean cpu_usage;
        private long online_cpus;
        private long system_cpu_usage;
        private ThrottlingDataBean throttling_data;

        @Data
        public static class CpuUsageBean {
            /**
             * percpu_usage : [13859735,18729501,3726455,264890]
             * total_usage : 36580581
             * usage_in_kernelmode : 20000000
             * usage_in_usermode : 10000000
             */

            private long total_usage;
            private long usage_in_kernelmode;
            private long usage_in_usermode;
            private List<Long> percpu_usage;
        }

        @Data
        public static class ThrottlingDataBean {
            /**
             * periods : 0
             * throttled_periods : 0
             * throttled_time : 0
             */

            private long periods;
            private long throttled_periods;
            private long throttled_time;
        }
    }

    @Data
    public static class MemoryStatsBean {
        /**
         * failcnt : 0
         * limit : 39997440
         * max_usage : 1576960
         * stats : {"active_anon":1437696,"active_file":0,"cache":12288,"hierarchical_memory_limit":39997440,"inactive_anon":4096,"inactive_file":8192,"mapped_file":4096,"pgfault":1541,"pgmajfault":0,"pgpgin":643,"pgpgout":289,"rss":1437696,"rss_huge":0,"total_active_anon":1437696,"total_active_file":0,"total_cache":12288,"total_inactive_anon":4096,"total_inactive_file":8192,"total_mapped_file":4096,"total_pgfault":1541,"total_pgmajfault":0,"total_pgpgin":643,"total_pgpgout":289,"total_rss":1437696,"total_rss_huge":0,"total_unevictable":0,"total_writeback":0,"unevictable":0,"writeback":0}
         * usage : 1449984
         */

        private long failcnt;
        private long limit;
        private long max_usage;
        private StatsBean stats;
        private long usage;

        @Data
        public static class StatsBean {
            /**
             * active_anon : 1437696
             * active_file : 0
             * cache : 12288
             * hierarchical_memory_limit : 39997440
             * inactive_anon : 4096
             * inactive_file : 8192
             * mapped_file : 4096
             * pgfault : 1541
             * pgmajfault : 0
             * pgpgin : 643
             * pgpgout : 289
             * rss : 1437696
             * rss_huge : 0
             * total_active_anon : 1437696
             * total_active_file : 0
             * total_cache : 12288
             * total_inactive_anon : 4096
             * total_inactive_file : 8192
             * total_mapped_file : 4096
             * total_pgfault : 1541
             * total_pgmajfault : 0
             * total_pgpgin : 643
             * total_pgpgout : 289
             * total_rss : 1437696
             * total_rss_huge : 0
             * total_unevictable : 0
             * total_writeback : 0
             * unevictable : 0
             * writeback : 0
             */

            private long active_anon;
            private long active_file;
            private long cache;
            private long hierarchical_memory_limit;
            private long inactive_anon;
            private long inactive_file;
            private long mapped_file;
            private long pgfault;
            private long pgmajfault;
            private long pgpgin;
            private long pgpgout;
            private long rss;
            private long rss_huge;
            private long total_active_anon;
            private long total_active_file;
            private long total_cache;
            private long total_inactive_anon;
            private long total_inactive_file;
            private long total_mapped_file;
            private long total_pgfault;
            private long total_pgmajfault;
            private long total_pgpgin;
            private long total_pgpgout;
            private long total_rss;
            private long total_rss_huge;
            private long total_unevictable;
            private long total_writeback;
            private long unevictable;
            private long writeback;
        }
    }

    @Data
    public static class NetworksBean {
        /**
         * eth0 : {"rx_bytes":3000,"rx_dropped":0,"rx_errors":0,"rx_packets":48,"tx_bytes":656,"tx_dropped":0,"tx_errors":0,"tx_packets":8}
         * eth5 : {"rx_bytes":0,"rx_dropped":0,"rx_errors":0,"rx_packets":0,"tx_bytes":0,"tx_dropped":0,"tx_errors":0,"tx_packets":0}
         */

        private Eth0Bean eth0;
        private Eth5Bean eth5;

        @Data
        public static class Eth0Bean {
            /**
             * rx_bytes : 3000
             * rx_dropped : 0
             * rx_errors : 0
             * rx_packets : 48
             * tx_bytes : 656
             * tx_dropped : 0
             * tx_errors : 0
             * tx_packets : 8
             */

            private int rx_bytes;
            private int rx_dropped;
            private int rx_errors;
            private int rx_packets;
            private int tx_bytes;
            private int tx_dropped;
            private int tx_errors;
            private int tx_packets;
        }

        @Data
        public static class Eth5Bean {
            /**
             * rx_bytes : 0
             * rx_dropped : 0
             * rx_errors : 0
             * rx_packets : 0
             * tx_bytes : 0
             * tx_dropped : 0
             * tx_errors : 0
             * tx_packets : 0
             */

            private int rx_bytes;
            private int rx_dropped;
            private int rx_errors;
            private int rx_packets;
            private int tx_bytes;
            private int tx_dropped;
            private int tx_errors;
            private int tx_packets;
        }
    }

    @Data
    public static class PidsStatsBean {
        /**
         * current : 2
         */

        private int current;
    }

    @Data
    public static class PrecpuStatsBean {
        /**
         * cpu_usage : {"percpu_usage":[13859735,18729501,3726455,264890],"total_usage":36580581,"usage_in_kernelmode":20000000,"usage_in_usermode":10000000}
         * online_cpus : 0
         * system_cpu_usage : 792714640000000
         * throttling_data : {"periods":0,"throttled_periods":0,"throttled_time":0}
         */

        private CpuUsageBeanX cpu_usage;
        private int online_cpus;
        private long system_cpu_usage;
        private ThrottlingDataBeanX throttling_data;

        @Data
        public static class CpuUsageBeanX {
            /**
             * percpu_usage : [13859735,18729501,3726455,264890]
             * total_usage : 36580581
             * usage_in_kernelmode : 20000000
             * usage_in_usermode : 10000000
             */

            private int total_usage;
            private int usage_in_kernelmode;
            private int usage_in_usermode;
            private List<Integer> percpu_usage;
        }

        @Data
        public static class ThrottlingDataBeanX {
            /**
             * periods : 0
             * throttled_periods : 0
             * throttled_time : 0
             */

            private int periods;
            private int throttled_periods;
            private int throttled_time;
        }
    }
}
