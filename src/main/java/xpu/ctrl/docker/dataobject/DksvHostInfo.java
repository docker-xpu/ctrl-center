package xpu.ctrl.docker.dataobject;

import lombok.Data;

import java.util.List;

@Data
public class DksvHostInfo {

    /**
     * cpu_info : {"logical_cores":4,"physical_cores":4,"percent":[1.0050251255959877]}
     * disk_info : {"path":"/","fstype":"xfs","total":107321753600,"free":81013366784,"used":26308386816,"usedPercent":24.513564057156813,"inodesTotal":52428800,"inodesUsed":134712,"inodesFree":52294088,"inodesUsedPercent":0.2569427490234375}
     * host_info : {"hostname":"localhost.localdomain","uptime":321047,"bootTime":1583856344,"procs":224,"os":"linux","platform":"centos","platformFamily":"rhel","platformVersion":"7.4.1708","kernelVersion":"3.10.0-693.el7.x86_64","kernelArch":"x86_64","virtualizationSystem":"","virtualizationRole":"","hostid":"988f4d56-14e1-6811-b03f-0da1a091b9e1"}
     * io_counters : [{"name":"all","bytesSent":1340378000,"bytesRecv":4188816866,"packetsSent":1766831,"packetsRecv":2663006,"errin":0,"errout":0,"dropin":0,"dropout":0,"fifoin":0,"fifoout":0}]
     * load_info : {"load":{"load1":0.01,"load5":0.02,"load15":0.05},"misc":{"procsTotal":491,"procsRunning":1,"procsBlocked":0,"ctxt":411083782}}
     * mem_info : {"swap_memory":{"total":8589930496,"used":70492160,"free":8519438336,"usedPercent":0.8206371405778602,"sin":31539200,"sout":249606144,"pgin":92413403136,"pgout":191359270912,"pgfault":2707471446016},"virtual_memory":{"total":8185933824,"available":4416376832,"used":3437543424,"usedPercent":41.993296035714444,"free":1240518656,"active":2049691648,"inactive":1947078656,"wired":0,"laundry":0,"buffers":0,"cached":3507871744,"writeback":0,"dirty":8192,"writebacktmp":0,"shared":18771968,"slab":372494336,"sreclaimable":221261824,"sunreclaim":151232512,"pagetables":31903744,"swapcached":7581696,"commitlimit":12682895360,"committedas":3925381120,"hightotal":0,"highfree":0,"lowtotal":0,"lowfree":0,"swaptotal":8589930496,"swapfree":8519438336,"mapped":112197632,"vmalloctotal":35184372087808,"vmallocused":211181568,"vmallocchunk":35183933779968,"hugepagestotal":0,"hugepagesfree":0,"hugepagesize":2097152}}
     * process : [1,2,3,5,7,8,9,10,11,12,13,15,16,17,18,20,21,22,23,25,27,28,29,30,31,32,33,34,41,42,43,44,52,54,57,59,79,113,298,299,304,307,308,310,311,312,313,316,350,355,393,394,405,406,419,420,421,422,423,424,425,426,427,428,429,501,523,524,525,531,564,605,609,610,611,612,613,614,615,652,653,661,662,663,664,665,666,668,669,721,723,725,749,750,752,753,754,755,756,757,758,760,762,763,768,772,778,785,786,788,799,804,820,848,892,1043,1044,1260,1261,1262,1270,1280,1654,1690,1858,1862,9045,9103,9104,13664,13671,13737,13798,13799,13817,13892,13907,13912,13919,13920,13988,13993,14085,14104,14109,14112,14131,14143,14157,14162,14167,14169,14177,14182,14189,14194,14203,14206,14208,14213,14225,14245,14254,14264,14274,14295,14312,14321,14327,14348,14355,14358,14363,14364,14376,14380,14389,14392,14410,14519,14522,14543,14548,14572,14625,14817,15118,15134,19761,21006,25171,25172,27791,27812,38842,54451,55782,60954,61220,62133,63072,63521,63859,64248,64464,64468,73592,73600,78415,90377,92363,92364,92365,92367,126390,126398,127108,127446,127474,128744,128827,128839,128962,129840]
     * proto_counters : [{"protocol":"ip","stats":{"DefaultTTL":64,"ForwDatagrams":20428,"Forwarding":1,"FragCreates":0,"FragFails":0,"FragOKs":0,"InAddrErrors":0,"InDelivers":1765958,"InDiscards":0,"InHdrErrors":0,"InReceives":1786647,"InUnknownProtos":0,"OutDiscards":737,"OutNoRoutes":292,"OutRequests":1398555,"ReasmFails":0,"ReasmOKs":0,"ReasmReqds":0,"ReasmTimeout":0}},{"protocol":"icmp","stats":{"InAddrMaskReps":0,"InAddrMasks":0,"InCsumErrors":815,"InDestUnreachs":327,"InEchoReps":1053,"InEchos":3,"InErrors":823,"InMsgs":2198,"InParmProbs":0,"InRedirects":0,"InSrcQuenchs":0,"longimeExcds":0,"longimestampReps":0,"longimestamps":0,"OutAddrMaskReps":0,"OutAddrMasks":0,"OutDestUnreachs":406,"OutEchoReps":3,"OutEchos":1198,"OutErrors":0,"OutMsgs":2542,"OutParmProbs":0,"OutRedirects":935,"OutSrcQuenchs":0,"OutTimeExcds":0,"OutTimestampReps":0,"OutTimestamps":0}},{"protocol":"icmpmsg","stats":{"longype0":1053,"longype3":327,"longype8":3,"OutType0":3,"OutType3":406,"OutType5":935,"OutType8":1198}},{"protocol":"tcp","stats":{"ActiveOpens":1897,"AttemptFails":34,"CurrEstab":3,"EstabResets":135,"InCsumErrors":0,"InErrs":1,"InSegs":1751536,"MaxConn":-1,"OutRsts":1951,"OutSegs":1721569,"PassiveOpens":952,"RetransSegs":2980,"RtoAlgorithm":1,"RtoMax":120000,"RtoMin":200}},{"protocol":"udp","stats":{"InCsumErrors":0,"InDatagrams":9662,"InErrors":0,"NoPorts":81,"OutDatagrams":6545,"RcvbufErrors":0,"SndbufErrors":0}},{"protocol":"udplite","stats":{"InCsumErrors":0,"InDatagrams":0,"InErrors":0,"NoPorts":0,"OutDatagrams":0,"RcvbufErrors":0,"SndbufErrors":0}}]
     * sensors : null
     */

    private CpuInfoBean cpu_info;
    private DiskInfoBean disk_info;
    private HostInfoBean host_info;
    private LoadInfoBean load_info;
    private MemInfoBean mem_info;
    private Object sensors;
    private List<IoCountersBean> io_counters;
    private List<Long> process;
    private List<ProtoCountersBean> proto_counters;

    public CpuInfoBean getCpu_info() {
        return cpu_info;
    }

    public void setCpu_info(CpuInfoBean cpu_info) {
        this.cpu_info = cpu_info;
    }

    public DiskInfoBean getDisk_info() {
        return disk_info;
    }

    public void setDisk_info(DiskInfoBean disk_info) {
        this.disk_info = disk_info;
    }

    public HostInfoBean getHost_info() {
        return host_info;
    }

    public void setHost_info(HostInfoBean host_info) {
        this.host_info = host_info;
    }

    public LoadInfoBean getLoad_info() {
        return load_info;
    }

    public void setLoad_info(LoadInfoBean load_info) {
        this.load_info = load_info;
    }

    public MemInfoBean getMem_info() {
        return mem_info;
    }

    public void setMem_info(MemInfoBean mem_info) {
        this.mem_info = mem_info;
    }

    public Object getSensors() {
        return sensors;
    }

    public void setSensors(Object sensors) {
        this.sensors = sensors;
    }

    public List<IoCountersBean> getIo_counters() {
        return io_counters;
    }

    public void setIo_counters(List<IoCountersBean> io_counters) {
        this.io_counters = io_counters;
    }

    public List<Long> getProcess() {
        return process;
    }

    public void setProcess(List<Long> process) {
        this.process = process;
    }

    public List<ProtoCountersBean> getProto_counters() {
        return proto_counters;
    }

    public void setProto_counters(List<ProtoCountersBean> proto_counters) {
        this.proto_counters = proto_counters;
    }

    public static class CpuInfoBean {
        /**
         * logical_cores : 4
         * physical_cores : 4
         * percent : [1.0050251255959877]
         */

        private long logical_cores;
        private long physical_cores;
        private List<Double> percent;

        public long getLogical_cores() {
            return logical_cores;
        }

        public void setLogical_cores(long logical_cores) {
            this.logical_cores = logical_cores;
        }

        public long getPhysical_cores() {
            return physical_cores;
        }

        public void setPhysical_cores(long physical_cores) {
            this.physical_cores = physical_cores;
        }

        public List<Double> getPercent() {
            return percent;
        }

        public void setPercent(List<Double> percent) {
            this.percent = percent;
        }
    }

    public static class DiskInfoBean {
        /**
         * path : /
         * fstype : xfs
         * total : 107321753600
         * free : 81013366784
         * used : 26308386816
         * usedPercent : 24.513564057156813
         * inodesTotal : 52428800
         * inodesUsed : 134712
         * inodesFree : 52294088
         * inodesUsedPercent : 0.2569427490234375
         */

        private String path;
        private String fstype;
        private long total;
        private long free;
        private long used;
        private double usedPercent;
        private long inodesTotal;
        private long inodesUsed;
        private long inodesFree;
        private double inodesUsedPercent;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFstype() {
            return fstype;
        }

        public void setFstype(String fstype) {
            this.fstype = fstype;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getFree() {
            return free;
        }

        public void setFree(long free) {
            this.free = free;
        }

        public long getUsed() {
            return used;
        }

        public void setUsed(long used) {
            this.used = used;
        }

        public double getUsedPercent() {
            return usedPercent;
        }

        public void setUsedPercent(double usedPercent) {
            this.usedPercent = usedPercent;
        }

        public long getInodesTotal() {
            return inodesTotal;
        }

        public void setInodesTotal(long inodesTotal) {
            this.inodesTotal = inodesTotal;
        }

        public long getInodesUsed() {
            return inodesUsed;
        }

        public void setInodesUsed(long inodesUsed) {
            this.inodesUsed = inodesUsed;
        }

        public long getInodesFree() {
            return inodesFree;
        }

        public void setInodesFree(long inodesFree) {
            this.inodesFree = inodesFree;
        }

        public double getInodesUsedPercent() {
            return inodesUsedPercent;
        }

        public void setInodesUsedPercent(double inodesUsedPercent) {
            this.inodesUsedPercent = inodesUsedPercent;
        }
    }

    public static class HostInfoBean {
        /**
         * hostname : localhost.localdomain
         * uptime : 321047
         * bootTime : 1583856344
         * procs : 224
         * os : linux
         * platform : centos
         * platformFamily : rhel
         * platformVersion : 7.4.1708
         * kernelVersion : 3.10.0-693.el7.x86_64
         * kernelArch : x86_64
         * virtualizationSystem :
         * virtualizationRole :
         * hostid : 988f4d56-14e1-6811-b03f-0da1a091b9e1
         */

        private String hostname;
        private long uptime;
        private long bootTime;
        private long procs;
        private String os;
        private String platform;
        private String platformFamily;
        private String platformVersion;
        private String kernelVersion;
        private String kernelArch;
        private String virtualizationSystem;
        private String virtualizationRole;
        private String hostid;

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public long getUptime() {
            return uptime;
        }

        public void setUptime(long uptime) {
            this.uptime = uptime;
        }

        public long getBootTime() {
            return bootTime;
        }

        public void setBootTime(long bootTime) {
            this.bootTime = bootTime;
        }

        public long getProcs() {
            return procs;
        }

        public void setProcs(long procs) {
            this.procs = procs;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getPlatformFamily() {
            return platformFamily;
        }

        public void setPlatformFamily(String platformFamily) {
            this.platformFamily = platformFamily;
        }

        public String getPlatformVersion() {
            return platformVersion;
        }

        public void setPlatformVersion(String platformVersion) {
            this.platformVersion = platformVersion;
        }

        public String getKernelVersion() {
            return kernelVersion;
        }

        public void setKernelVersion(String kernelVersion) {
            this.kernelVersion = kernelVersion;
        }

        public String getKernelArch() {
            return kernelArch;
        }

        public void setKernelArch(String kernelArch) {
            this.kernelArch = kernelArch;
        }

        public String getVirtualizationSystem() {
            return virtualizationSystem;
        }

        public void setVirtualizationSystem(String virtualizationSystem) {
            this.virtualizationSystem = virtualizationSystem;
        }

        public String getVirtualizationRole() {
            return virtualizationRole;
        }

        public void setVirtualizationRole(String virtualizationRole) {
            this.virtualizationRole = virtualizationRole;
        }

        public String getHostid() {
            return hostid;
        }

        public void setHostid(String hostid) {
            this.hostid = hostid;
        }
    }

    public static class LoadInfoBean {
        /**
         * load : {"load1":0.01,"load5":0.02,"load15":0.05}
         * misc : {"procsTotal":491,"procsRunning":1,"procsBlocked":0,"ctxt":411083782}
         */

        private LoadBean load;
        private MiscBean misc;

        public LoadBean getLoad() {
            return load;
        }

        public void setLoad(LoadBean load) {
            this.load = load;
        }

        public MiscBean getMisc() {
            return misc;
        }

        public void setMisc(MiscBean misc) {
            this.misc = misc;
        }

        public static class LoadBean {
            /**
             * load1 : 0.01
             * load5 : 0.02
             * load15 : 0.05
             */

            private double load1;
            private double load5;
            private double load15;

            public double getLoad1() {
                return load1;
            }

            public void setLoad1(double load1) {
                this.load1 = load1;
            }

            public double getLoad5() {
                return load5;
            }

            public void setLoad5(double load5) {
                this.load5 = load5;
            }

            public double getLoad15() {
                return load15;
            }

            public void setLoad15(double load15) {
                this.load15 = load15;
            }
        }

        public static class MiscBean {
            /**
             * procsTotal : 491
             * procsRunning : 1
             * procsBlocked : 0
             * ctxt : 411083782
             */

            private long procsTotal;
            private long procsRunning;
            private long procsBlocked;
            private long ctxt;

            public long getProcsTotal() {
                return procsTotal;
            }

            public void setProcsTotal(long procsTotal) {
                this.procsTotal = procsTotal;
            }

            public long getProcsRunning() {
                return procsRunning;
            }

            public void setProcsRunning(long procsRunning) {
                this.procsRunning = procsRunning;
            }

            public long getProcsBlocked() {
                return procsBlocked;
            }

            public void setProcsBlocked(long procsBlocked) {
                this.procsBlocked = procsBlocked;
            }

            public long getCtxt() {
                return ctxt;
            }

            public void setCtxt(long ctxt) {
                this.ctxt = ctxt;
            }
        }
    }

    public static class MemInfoBean {
        /**
         * swap_memory : {"total":8589930496,"used":70492160,"free":8519438336,"usedPercent":0.8206371405778602,"sin":31539200,"sout":249606144,"pgin":92413403136,"pgout":191359270912,"pgfault":2707471446016}
         * virtual_memory : {"total":8185933824,"available":4416376832,"used":3437543424,"usedPercent":41.993296035714444,"free":1240518656,"active":2049691648,"inactive":1947078656,"wired":0,"laundry":0,"buffers":0,"cached":3507871744,"writeback":0,"dirty":8192,"writebacktmp":0,"shared":18771968,"slab":372494336,"sreclaimable":221261824,"sunreclaim":151232512,"pagetables":31903744,"swapcached":7581696,"commitlimit":12682895360,"committedas":3925381120,"hightotal":0,"highfree":0,"lowtotal":0,"lowfree":0,"swaptotal":8589930496,"swapfree":8519438336,"mapped":112197632,"vmalloctotal":35184372087808,"vmallocused":211181568,"vmallocchunk":35183933779968,"hugepagestotal":0,"hugepagesfree":0,"hugepagesize":2097152}
         */

        private SwapMemoryBean swap_memory;
        private VirtualMemoryBean virtual_memory;

        public SwapMemoryBean getSwap_memory() {
            return swap_memory;
        }

        public void setSwap_memory(SwapMemoryBean swap_memory) {
            this.swap_memory = swap_memory;
        }

        public VirtualMemoryBean getVirtual_memory() {
            return virtual_memory;
        }

        public void setVirtual_memory(VirtualMemoryBean virtual_memory) {
            this.virtual_memory = virtual_memory;
        }

        public static class SwapMemoryBean {
            /**
             * total : 8589930496
             * used : 70492160
             * free : 8519438336
             * usedPercent : 0.8206371405778602
             * sin : 31539200
             * sout : 249606144
             * pgin : 92413403136
             * pgout : 191359270912
             * pgfault : 2707471446016
             */

            private long total;
            private long used;
            private long free;
            private double usedPercent;
            private long sin;
            private long sout;
            private long pgin;
            private long pgout;
            private long pgfault;

            public long getTotal() {
                return total;
            }

            public void setTotal(long total) {
                this.total = total;
            }

            public long getUsed() {
                return used;
            }

            public void setUsed(long used) {
                this.used = used;
            }

            public long getFree() {
                return free;
            }

            public void setFree(long free) {
                this.free = free;
            }

            public double getUsedPercent() {
                return usedPercent;
            }

            public void setUsedPercent(double usedPercent) {
                this.usedPercent = usedPercent;
            }

            public long getSin() {
                return sin;
            }

            public void setSin(long sin) {
                this.sin = sin;
            }

            public long getSout() {
                return sout;
            }

            public void setSout(long sout) {
                this.sout = sout;
            }

            public long getPgin() {
                return pgin;
            }

            public void setPgin(long pgin) {
                this.pgin = pgin;
            }

            public long getPgout() {
                return pgout;
            }

            public void setPgout(long pgout) {
                this.pgout = pgout;
            }

            public long getPgfault() {
                return pgfault;
            }

            public void setPgfault(long pgfault) {
                this.pgfault = pgfault;
            }
        }

        public static class VirtualMemoryBean {
            /**
             * total : 8185933824
             * available : 4416376832
             * used : 3437543424
             * usedPercent : 41.993296035714444
             * free : 1240518656
             * active : 2049691648
             * inactive : 1947078656
             * wired : 0
             * laundry : 0
             * buffers : 0
             * cached : 3507871744
             * writeback : 0
             * dirty : 8192
             * writebacktmp : 0
             * shared : 18771968
             * slab : 372494336
             * sreclaimable : 221261824
             * sunreclaim : 151232512
             * pagetables : 31903744
             * swapcached : 7581696
             * commitlimit : 12682895360
             * committedas : 3925381120
             * hightotal : 0
             * highfree : 0
             * lowtotal : 0
             * lowfree : 0
             * swaptotal : 8589930496
             * swapfree : 8519438336
             * mapped : 112197632
             * vmalloctotal : 35184372087808
             * vmallocused : 211181568
             * vmallocchunk : 35183933779968
             * hugepagestotal : 0
             * hugepagesfree : 0
             * hugepagesize : 2097152
             */

            private long total;
            private long available;
            private long used;
            private double usedPercent;
            private long free;
            private long active;
            private long inactive;
            private long wired;
            private long laundry;
            private long buffers;
            private long cached;
            private long writeback;
            private long dirty;
            private long writebacktmp;
            private long shared;
            private long slab;
            private long sreclaimable;
            private long sunreclaim;
            private long pagetables;
            private long swapcached;
            private long commitlimit;
            private long committedas;
            private long hightotal;
            private long highfree;
            private long lowtotal;
            private long lowfree;
            private long swaptotal;
            private long swapfree;
            private long mapped;
            private long vmalloctotal;
            private long vmallocused;
            private long vmallocchunk;
            private long hugepagestotal;
            private long hugepagesfree;
            private long hugepagesize;

            public long getTotal() {
                return total;
            }

            public void setTotal(long total) {
                this.total = total;
            }

            public long getAvailable() {
                return available;
            }

            public void setAvailable(long available) {
                this.available = available;
            }

            public long getUsed() {
                return used;
            }

            public void setUsed(long used) {
                this.used = used;
            }

            public double getUsedPercent() {
                return usedPercent;
            }

            public void setUsedPercent(double usedPercent) {
                this.usedPercent = usedPercent;
            }

            public long getFree() {
                return free;
            }

            public void setFree(long free) {
                this.free = free;
            }

            public long getActive() {
                return active;
            }

            public void setActive(long active) {
                this.active = active;
            }

            public long getInactive() {
                return inactive;
            }

            public void setInactive(long inactive) {
                this.inactive = inactive;
            }

            public long getWired() {
                return wired;
            }

            public void setWired(long wired) {
                this.wired = wired;
            }

            public long getLaundry() {
                return laundry;
            }

            public void setLaundry(long laundry) {
                this.laundry = laundry;
            }

            public long getBuffers() {
                return buffers;
            }

            public void setBuffers(long buffers) {
                this.buffers = buffers;
            }

            public long getCached() {
                return cached;
            }

            public void setCached(long cached) {
                this.cached = cached;
            }

            public long getWriteback() {
                return writeback;
            }

            public void setWriteback(long writeback) {
                this.writeback = writeback;
            }

            public long getDirty() {
                return dirty;
            }

            public void setDirty(long dirty) {
                this.dirty = dirty;
            }

            public long getWritebacktmp() {
                return writebacktmp;
            }

            public void setWritebacktmp(long writebacktmp) {
                this.writebacktmp = writebacktmp;
            }

            public long getShared() {
                return shared;
            }

            public void setShared(long shared) {
                this.shared = shared;
            }

            public long getSlab() {
                return slab;
            }

            public void setSlab(long slab) {
                this.slab = slab;
            }

            public long getSreclaimable() {
                return sreclaimable;
            }

            public void setSreclaimable(long sreclaimable) {
                this.sreclaimable = sreclaimable;
            }

            public long getSunreclaim() {
                return sunreclaim;
            }

            public void setSunreclaim(long sunreclaim) {
                this.sunreclaim = sunreclaim;
            }

            public long getPagetables() {
                return pagetables;
            }

            public void setPagetables(long pagetables) {
                this.pagetables = pagetables;
            }

            public long getSwapcached() {
                return swapcached;
            }

            public void setSwapcached(long swapcached) {
                this.swapcached = swapcached;
            }

            public long getCommitlimit() {
                return commitlimit;
            }

            public void setCommitlimit(long commitlimit) {
                this.commitlimit = commitlimit;
            }

            public long getCommittedas() {
                return committedas;
            }

            public void setCommittedas(long committedas) {
                this.committedas = committedas;
            }

            public long getHightotal() {
                return hightotal;
            }

            public void setHightotal(long hightotal) {
                this.hightotal = hightotal;
            }

            public long getHighfree() {
                return highfree;
            }

            public void setHighfree(long highfree) {
                this.highfree = highfree;
            }

            public long getLowtotal() {
                return lowtotal;
            }

            public void setLowtotal(long lowtotal) {
                this.lowtotal = lowtotal;
            }

            public long getLowfree() {
                return lowfree;
            }

            public void setLowfree(long lowfree) {
                this.lowfree = lowfree;
            }

            public long getSwaptotal() {
                return swaptotal;
            }

            public void setSwaptotal(long swaptotal) {
                this.swaptotal = swaptotal;
            }

            public long getSwapfree() {
                return swapfree;
            }

            public void setSwapfree(long swapfree) {
                this.swapfree = swapfree;
            }

            public long getMapped() {
                return mapped;
            }

            public void setMapped(long mapped) {
                this.mapped = mapped;
            }

            public long getVmalloctotal() {
                return vmalloctotal;
            }

            public void setVmalloctotal(long vmalloctotal) {
                this.vmalloctotal = vmalloctotal;
            }

            public long getVmallocused() {
                return vmallocused;
            }

            public void setVmallocused(long vmallocused) {
                this.vmallocused = vmallocused;
            }

            public long getVmallocchunk() {
                return vmallocchunk;
            }

            public void setVmallocchunk(long vmallocchunk) {
                this.vmallocchunk = vmallocchunk;
            }

            public long getHugepagestotal() {
                return hugepagestotal;
            }

            public void setHugepagestotal(long hugepagestotal) {
                this.hugepagestotal = hugepagestotal;
            }

            public long getHugepagesfree() {
                return hugepagesfree;
            }

            public void setHugepagesfree(long hugepagesfree) {
                this.hugepagesfree = hugepagesfree;
            }

            public long getHugepagesize() {
                return hugepagesize;
            }

            public void setHugepagesize(long hugepagesize) {
                this.hugepagesize = hugepagesize;
            }
        }
    }

    public static class IoCountersBean {
        /**
         * name : all
         * bytesSent : 1340378000
         * bytesRecv : 4188816866
         * packetsSent : 1766831
         * packetsRecv : 2663006
         * errin : 0
         * errout : 0
         * dropin : 0
         * dropout : 0
         * fifoin : 0
         * fifoout : 0
         */

        private String name;
        private long bytesSent;
        private long bytesRecv;
        private long packetsSent;
        private long packetsRecv;
        private long errin;
        private long errout;
        private long dropin;
        private long dropout;
        private long fifoin;
        private long fifoout;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getBytesSent() {
            return bytesSent;
        }

        public void setBytesSent(long bytesSent) {
            this.bytesSent = bytesSent;
        }

        public long getBytesRecv() {
            return bytesRecv;
        }

        public void setBytesRecv(long bytesRecv) {
            this.bytesRecv = bytesRecv;
        }

        public long getPacketsSent() {
            return packetsSent;
        }

        public void setPacketsSent(long packetsSent) {
            this.packetsSent = packetsSent;
        }

        public long getPacketsRecv() {
            return packetsRecv;
        }

        public void setPacketsRecv(long packetsRecv) {
            this.packetsRecv = packetsRecv;
        }

        public long getErrin() {
            return errin;
        }

        public void setErrin(long errin) {
            this.errin = errin;
        }

        public long getErrout() {
            return errout;
        }

        public void setErrout(long errout) {
            this.errout = errout;
        }

        public long getDropin() {
            return dropin;
        }

        public void setDropin(long dropin) {
            this.dropin = dropin;
        }

        public long getDropout() {
            return dropout;
        }

        public void setDropout(long dropout) {
            this.dropout = dropout;
        }

        public long getFifoin() {
            return fifoin;
        }

        public void setFifoin(long fifoin) {
            this.fifoin = fifoin;
        }

        public long getFifoout() {
            return fifoout;
        }

        public void setFifoout(long fifoout) {
            this.fifoout = fifoout;
        }
    }

    public static class ProtoCountersBean {
        /**
         * protocol : ip
         * stats : {"DefaultTTL":64,"ForwDatagrams":20428,"Forwarding":1,"FragCreates":0,"FragFails":0,"FragOKs":0,"InAddrErrors":0,"InDelivers":1765958,"InDiscards":0,"InHdrErrors":0,"InReceives":1786647,"InUnknownProtos":0,"OutDiscards":737,"OutNoRoutes":292,"OutRequests":1398555,"ReasmFails":0,"ReasmOKs":0,"ReasmReqds":0,"ReasmTimeout":0}
         */

        private String protocol;
        private StatsBean stats;

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public StatsBean getStats() {
            return stats;
        }

        public void setStats(StatsBean stats) {
            this.stats = stats;
        }

        public static class StatsBean {
            /**
             * DefaultTTL : 64
             * ForwDatagrams : 20428
             * Forwarding : 1
             * FragCreates : 0
             * FragFails : 0
             * FragOKs : 0
             * InAddrErrors : 0
             * InDelivers : 1765958
             * InDiscards : 0
             * InHdrErrors : 0
             * InReceives : 1786647
             * InUnknownProtos : 0
             * OutDiscards : 737
             * OutNoRoutes : 292
             * OutRequests : 1398555
             * ReasmFails : 0
             * ReasmOKs : 0
             * ReasmReqds : 0
             * ReasmTimeout : 0
             */

            private long DefaultTTL;
            private long ForwDatagrams;
            private long Forwarding;
            private long FragCreates;
            private long FragFails;
            private long FragOKs;
            private long InAddrErrors;
            private long InDelivers;
            private long InDiscards;
            private long InHdrErrors;
            private long InReceives;
            private long InUnknownProtos;
            private long OutDiscards;
            private long OutNoRoutes;
            private long OutRequests;
            private long ReasmFails;
            private long ReasmOKs;
            private long ReasmReqds;
            private long ReasmTimeout;

            public long getDefaultTTL() {
                return DefaultTTL;
            }

            public void setDefaultTTL(long DefaultTTL) {
                this.DefaultTTL = DefaultTTL;
            }

            public long getForwDatagrams() {
                return ForwDatagrams;
            }

            public void setForwDatagrams(long ForwDatagrams) {
                this.ForwDatagrams = ForwDatagrams;
            }

            public long getForwarding() {
                return Forwarding;
            }

            public void setForwarding(long Forwarding) {
                this.Forwarding = Forwarding;
            }

            public long getFragCreates() {
                return FragCreates;
            }

            public void setFragCreates(long FragCreates) {
                this.FragCreates = FragCreates;
            }

            public long getFragFails() {
                return FragFails;
            }

            public void setFragFails(long FragFails) {
                this.FragFails = FragFails;
            }

            public long getFragOKs() {
                return FragOKs;
            }

            public void setFragOKs(long FragOKs) {
                this.FragOKs = FragOKs;
            }

            public long getInAddrErrors() {
                return InAddrErrors;
            }

            public void setInAddrErrors(long InAddrErrors) {
                this.InAddrErrors = InAddrErrors;
            }

            public long getInDelivers() {
                return InDelivers;
            }

            public void setInDelivers(long InDelivers) {
                this.InDelivers = InDelivers;
            }

            public long getInDiscards() {
                return InDiscards;
            }

            public void setInDiscards(long InDiscards) {
                this.InDiscards = InDiscards;
            }

            public long getInHdrErrors() {
                return InHdrErrors;
            }

            public void setInHdrErrors(long InHdrErrors) {
                this.InHdrErrors = InHdrErrors;
            }

            public long getInReceives() {
                return InReceives;
            }

            public void setInReceives(long InReceives) {
                this.InReceives = InReceives;
            }

            public long getInUnknownProtos() {
                return InUnknownProtos;
            }

            public void setInUnknownProtos(long InUnknownProtos) {
                this.InUnknownProtos = InUnknownProtos;
            }

            public long getOutDiscards() {
                return OutDiscards;
            }

            public void setOutDiscards(long OutDiscards) {
                this.OutDiscards = OutDiscards;
            }

            public long getOutNoRoutes() {
                return OutNoRoutes;
            }

            public void setOutNoRoutes(long OutNoRoutes) {
                this.OutNoRoutes = OutNoRoutes;
            }

            public long getOutRequests() {
                return OutRequests;
            }

            public void setOutRequests(long OutRequests) {
                this.OutRequests = OutRequests;
            }

            public long getReasmFails() {
                return ReasmFails;
            }

            public void setReasmFails(long ReasmFails) {
                this.ReasmFails = ReasmFails;
            }

            public long getReasmOKs() {
                return ReasmOKs;
            }

            public void setReasmOKs(long ReasmOKs) {
                this.ReasmOKs = ReasmOKs;
            }

            public long getReasmReqds() {
                return ReasmReqds;
            }

            public void setReasmReqds(long ReasmReqds) {
                this.ReasmReqds = ReasmReqds;
            }

            public long getReasmTimeout() {
                return ReasmTimeout;
            }

            public void setReasmTimeout(long ReasmTimeout) {
                this.ReasmTimeout = ReasmTimeout;
            }
        }
    }
}
