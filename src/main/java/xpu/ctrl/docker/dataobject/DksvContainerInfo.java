package xpu.ctrl.docker.dataobject;

import java.math.BigInteger;
import java.util.List;

public class DksvContainerInfo {
    /**
     * container : {"Id":"6881f4d7d3f29b82932addf43916800dca8e7f7ec83eb744112975fdec99863d","Names":["/nginx-test"],"Image":"nginx:latest","ImageID":"sha256:6678c7c2e56c970388f8d5a398aa30f2ab60e85f20165e101053c3d3a11e6663","Command":"nginx -g 'daemon off;'","Created":1584251679,"Ports":[{"IP":"0.0.0.0","PrivatePort":80,"PublicPort":8081,"Type":"tcp"}],"Labels":{"maBigIntegerainer":"NGINX Docker MaBigIntegerainers <docker-maBigInteger@nginx.com>"},"State":"running","Status":"Up 19 seconds","HostConfig":{"NetworkMode":"default"},"NetworkSettings":{"Networks":{"bridge":{"IPAMConfig":null,"Links":null,"Aliases":null,"NetworkID":"3435d5d45626ddfe15a8f824bb4843ff90d9dd45359822a4c218ffc7d366ccdb","EndpoBigIntegerID":"213ec11121c89fd63c1f0214f4acdb1d085857ccd415d80a349062a251280533","Gateway":"172.17.0.1","IPAddress":"172.17.0.2","IPPrefixLen":16,"IPv6Gateway":"","GlobalIPv6Address":"","GlobalIPv6PrefixLen":0,"MacAddress":"02:42:ac:11:00:02","DriverOpts":null}}},"Mounts":[]}
     * mem_info : {"containerID":"6881f4d7d3f29b82932addf43916800dca8e7f7ec83eb744112975fdec99863d","cache":12288,"rss":1474560,"rssHuge":0,"mappedFile":0,"pgpgin":862,"pgpgout":499,"pgfault":2551,"pgmajfault":0,"inactiveAnon":4096,"activeAnon":1474560,"inactiveFile":8192,"activeFile":0,"unevictable":0,"hierarchicalMemoryLimit":9223372036854771712,"totalCache":12288,"totalRss":1474560,"totalRssHuge":0,"totalMappedFile":4096,"totalPgpgin":862,"totalPgpgout":499,"totalPgfault":2551,"totalPgmajfault":0,"totalInactiveAnon":4096,"totalActiveAnon":1474560,"totalInactiveFile":8192,"totalActiveFile":0,"totalUnevictable":0,"memUsageInBytes":1486848,"memMaxUsageInBytes":2080768,"memoryLimitInBbytes":0,"memoryFailcnt":0}
     * cpu_info : {"cpu":"6881f4d7d3f29b82932addf43916800dca8e7f7ec83eb744112975fdec99863d","user":0.03,"system":0.06,"idle":0,"nice":0,"iowait":0,"irq":0,"softirq":0,"steal":0,"guest":0,"guestNice":0}
     */

    private ContainerBean container;
    private MemInfoBean mem_info;
    private CpuInfoBean cpu_info;

    public ContainerBean getContainer() {
        return container;
    }

    public void setContainer(ContainerBean container) {
        this.container = container;
    }

    public MemInfoBean getMem_info() {
        return mem_info;
    }

    public void setMem_info(MemInfoBean mem_info) {
        this.mem_info = mem_info;
    }

    public CpuInfoBean getCpu_info() {
        return cpu_info;
    }

    public void setCpu_info(CpuInfoBean cpu_info) {
        this.cpu_info = cpu_info;
    }

    public static class ContainerBean {
        /**
         * Id : 6881f4d7d3f29b82932addf43916800dca8e7f7ec83eb744112975fdec99863d
         * Names : ["/nginx-test"]
         * Image : nginx:latest
         * ImageID : sha256:6678c7c2e56c970388f8d5a398aa30f2ab60e85f20165e101053c3d3a11e6663
         * Command : nginx -g 'daemon off;'
         * Created : 1584251679
         * Ports : [{"IP":"0.0.0.0","PrivatePort":80,"PublicPort":8081,"Type":"tcp"}]
         * Labels : {"maBigIntegerainer":"NGINX Docker MaBigIntegerainers <docker-maBigInteger@nginx.com>"}
         * State : running
         * Status : Up 19 seconds
         * HostConfig : {"NetworkMode":"default"}
         * NetworkSettings : {"Networks":{"bridge":{"IPAMConfig":null,"Links":null,"Aliases":null,"NetworkID":"3435d5d45626ddfe15a8f824bb4843ff90d9dd45359822a4c218ffc7d366ccdb","EndpoBigIntegerID":"213ec11121c89fd63c1f0214f4acdb1d085857ccd415d80a349062a251280533","Gateway":"172.17.0.1","IPAddress":"172.17.0.2","IPPrefixLen":16,"IPv6Gateway":"","GlobalIPv6Address":"","GlobalIPv6PrefixLen":0,"MacAddress":"02:42:ac:11:00:02","DriverOpts":null}}}
         * Mounts : []
         */

        private String Id;
        private String Image;
        private String ImageID;
        private String Command;
        private BigInteger Created;
        private LabelsBean Labels;
        private String State;
        private String Status;
        private HostConfigBean HostConfig;
        private NetworkSettingsBean NetworkSettings;
        private List<String> Names;
        private List<PortsBean> Ports;
        private List<?> Mounts;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getImageID() {
            return ImageID;
        }

        public void setImageID(String ImageID) {
            this.ImageID = ImageID;
        }

        public String getCommand() {
            return Command;
        }

        public void setCommand(String Command) {
            this.Command = Command;
        }

        public BigInteger getCreated() {
            return Created;
        }

        public void setCreated(BigInteger Created) {
            this.Created = Created;
        }

        public LabelsBean getLabels() {
            return Labels;
        }

        public void setLabels(LabelsBean Labels) {
            this.Labels = Labels;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public HostConfigBean getHostConfig() {
            return HostConfig;
        }

        public void setHostConfig(HostConfigBean HostConfig) {
            this.HostConfig = HostConfig;
        }

        public NetworkSettingsBean getNetworkSettings() {
            return NetworkSettings;
        }

        public void setNetworkSettings(NetworkSettingsBean NetworkSettings) {
            this.NetworkSettings = NetworkSettings;
        }

        public List<String> getNames() {
            return Names;
        }

        public void setNames(List<String> Names) {
            this.Names = Names;
        }

        public List<PortsBean> getPorts() {
            return Ports;
        }

        public void setPorts(List<PortsBean> Ports) {
            this.Ports = Ports;
        }

        public List<?> getMounts() {
            return Mounts;
        }

        public void setMounts(List<?> Mounts) {
            this.Mounts = Mounts;
        }

        public static class LabelsBean {
            /**
             * maBigIntegerainer : NGINX Docker MaBigIntegerainers <docker-maBigInteger@nginx.com>
             */

            private String maBigIntegerainer;

            public String getMaBigIntegerainer() {
                return maBigIntegerainer;
            }

            public void setMaBigIntegerainer(String maBigIntegerainer) {
                this.maBigIntegerainer = maBigIntegerainer;
            }
        }

        public static class HostConfigBean {
            /**
             * NetworkMode : default
             */

            private String NetworkMode;

            public String getNetworkMode() {
                return NetworkMode;
            }

            public void setNetworkMode(String NetworkMode) {
                this.NetworkMode = NetworkMode;
            }
        }

        public static class NetworkSettingsBean {
            /**
             * Networks : {"bridge":{"IPAMConfig":null,"Links":null,"Aliases":null,"NetworkID":"3435d5d45626ddfe15a8f824bb4843ff90d9dd45359822a4c218ffc7d366ccdb","EndpoBigIntegerID":"213ec11121c89fd63c1f0214f4acdb1d085857ccd415d80a349062a251280533","Gateway":"172.17.0.1","IPAddress":"172.17.0.2","IPPrefixLen":16,"IPv6Gateway":"","GlobalIPv6Address":"","GlobalIPv6PrefixLen":0,"MacAddress":"02:42:ac:11:00:02","DriverOpts":null}}
             */

            private NetworksBean Networks;

            public NetworksBean getNetworks() {
                return Networks;
            }

            public void setNetworks(NetworksBean Networks) {
                this.Networks = Networks;
            }

            public static class NetworksBean {
                /**
                 * bridge : {"IPAMConfig":null,"Links":null,"Aliases":null,"NetworkID":"3435d5d45626ddfe15a8f824bb4843ff90d9dd45359822a4c218ffc7d366ccdb","EndpoBigIntegerID":"213ec11121c89fd63c1f0214f4acdb1d085857ccd415d80a349062a251280533","Gateway":"172.17.0.1","IPAddress":"172.17.0.2","IPPrefixLen":16,"IPv6Gateway":"","GlobalIPv6Address":"","GlobalIPv6PrefixLen":0,"MacAddress":"02:42:ac:11:00:02","DriverOpts":null}
                 */

                private BridgeBean bridge;

                public BridgeBean getBridge() {
                    return bridge;
                }

                public void setBridge(BridgeBean bridge) {
                    this.bridge = bridge;
                }

                public static class BridgeBean {
                    /**
                     * IPAMConfig : null
                     * Links : null
                     * Aliases : null
                     * NetworkID : 3435d5d45626ddfe15a8f824bb4843ff90d9dd45359822a4c218ffc7d366ccdb
                     * EndpoBigIntegerID : 213ec11121c89fd63c1f0214f4acdb1d085857ccd415d80a349062a251280533
                     * Gateway : 172.17.0.1
                     * IPAddress : 172.17.0.2
                     * IPPrefixLen : 16
                     * IPv6Gateway : 
                     * GlobalIPv6Address : 
                     * GlobalIPv6PrefixLen : 0
                     * MacAddress : 02:42:ac:11:00:02
                     * DriverOpts : null
                     */

                    private Object IPAMConfig;
                    private Object Links;
                    private Object Aliases;
                    private String NetworkID;
                    private String EndpoBigIntegerID;
                    private String Gateway;
                    private String IPAddress;
                    private BigInteger IPPrefixLen;
                    private String IPv6Gateway;
                    private String GlobalIPv6Address;
                    private BigInteger GlobalIPv6PrefixLen;
                    private String MacAddress;
                    private Object DriverOpts;

                    public Object getIPAMConfig() {
                        return IPAMConfig;
                    }

                    public void setIPAMConfig(Object IPAMConfig) {
                        this.IPAMConfig = IPAMConfig;
                    }

                    public Object getLinks() {
                        return Links;
                    }

                    public void setLinks(Object Links) {
                        this.Links = Links;
                    }

                    public Object getAliases() {
                        return Aliases;
                    }

                    public void setAliases(Object Aliases) {
                        this.Aliases = Aliases;
                    }

                    public String getNetworkID() {
                        return NetworkID;
                    }

                    public void setNetworkID(String NetworkID) {
                        this.NetworkID = NetworkID;
                    }

                    public String getEndpoBigIntegerID() {
                        return EndpoBigIntegerID;
                    }

                    public void setEndpoBigIntegerID(String EndpoBigIntegerID) {
                        this.EndpoBigIntegerID = EndpoBigIntegerID;
                    }

                    public String getGateway() {
                        return Gateway;
                    }

                    public void setGateway(String Gateway) {
                        this.Gateway = Gateway;
                    }

                    public String getIPAddress() {
                        return IPAddress;
                    }

                    public void setIPAddress(String IPAddress) {
                        this.IPAddress = IPAddress;
                    }

                    public BigInteger getIPPrefixLen() {
                        return IPPrefixLen;
                    }

                    public void setIPPrefixLen(BigInteger IPPrefixLen) {
                        this.IPPrefixLen = IPPrefixLen;
                    }

                    public String getIPv6Gateway() {
                        return IPv6Gateway;
                    }

                    public void setIPv6Gateway(String IPv6Gateway) {
                        this.IPv6Gateway = IPv6Gateway;
                    }

                    public String getGlobalIPv6Address() {
                        return GlobalIPv6Address;
                    }

                    public void setGlobalIPv6Address(String GlobalIPv6Address) {
                        this.GlobalIPv6Address = GlobalIPv6Address;
                    }

                    public BigInteger getGlobalIPv6PrefixLen() {
                        return GlobalIPv6PrefixLen;
                    }

                    public void setGlobalIPv6PrefixLen(BigInteger GlobalIPv6PrefixLen) {
                        this.GlobalIPv6PrefixLen = GlobalIPv6PrefixLen;
                    }

                    public String getMacAddress() {
                        return MacAddress;
                    }

                    public void setMacAddress(String MacAddress) {
                        this.MacAddress = MacAddress;
                    }

                    public Object getDriverOpts() {
                        return DriverOpts;
                    }

                    public void setDriverOpts(Object DriverOpts) {
                        this.DriverOpts = DriverOpts;
                    }
                }
            }
        }

        public static class PortsBean {
            /**
             * IP : 0.0.0.0
             * PrivatePort : 80
             * PublicPort : 8081
             * Type : tcp
             */

            private String IP;
            private BigInteger PrivatePort;
            private BigInteger PublicPort;
            private String Type;

            public String getIP() {
                return IP;
            }

            public void setIP(String IP) {
                this.IP = IP;
            }

            public BigInteger getPrivatePort() {
                return PrivatePort;
            }

            public void setPrivatePort(BigInteger PrivatePort) {
                this.PrivatePort = PrivatePort;
            }

            public BigInteger getPublicPort() {
                return PublicPort;
            }

            public void setPublicPort(BigInteger PublicPort) {
                this.PublicPort = PublicPort;
            }

            public String getType() {
                return Type;
            }

            public void setType(String Type) {
                this.Type = Type;
            }
        }
    }

    public static class MemInfoBean {
        /**
         * containerID : 6881f4d7d3f29b82932addf43916800dca8e7f7ec83eb744112975fdec99863d
         * cache : 12288
         * rss : 1474560
         * rssHuge : 0
         * mappedFile : 0
         * pgpgin : 862
         * pgpgout : 499
         * pgfault : 2551
         * pgmajfault : 0
         * inactiveAnon : 4096
         * activeAnon : 1474560
         * inactiveFile : 8192
         * activeFile : 0
         * unevictable : 0
         * hierarchicalMemoryLimit : 9223372036854771712
         * totalCache : 12288
         * totalRss : 1474560
         * totalRssHuge : 0
         * totalMappedFile : 4096
         * totalPgpgin : 862
         * totalPgpgout : 499
         * totalPgfault : 2551
         * totalPgmajfault : 0
         * totalInactiveAnon : 4096
         * totalActiveAnon : 1474560
         * totalInactiveFile : 8192
         * totalActiveFile : 0
         * totalUnevictable : 0
         * memUsageInBytes : 1486848
         * memMaxUsageInBytes : 2080768
         * memoryLimitInBbytes : 0
         * memoryFailcnt : 0
         */

        private String containerID;
        private BigInteger cache;
        private BigInteger rss;
        private BigInteger rssHuge;
        private BigInteger mappedFile;
        private BigInteger pgpgin;
        private BigInteger pgpgout;
        private BigInteger pgfault;
        private BigInteger pgmajfault;
        private BigInteger inactiveAnon;
        private BigInteger activeAnon;
        private BigInteger inactiveFile;
        private BigInteger activeFile;
        private BigInteger unevictable;
        private BigInteger hierarchicalMemoryLimit;
        private BigInteger totalCache;
        private BigInteger totalRss;
        private BigInteger totalRssHuge;
        private BigInteger totalMappedFile;
        private BigInteger totalPgpgin;
        private BigInteger totalPgpgout;
        private BigInteger totalPgfault;
        private BigInteger totalPgmajfault;
        private BigInteger totalInactiveAnon;
        private BigInteger totalActiveAnon;
        private BigInteger totalInactiveFile;
        private BigInteger totalActiveFile;
        private BigInteger totalUnevictable;
        private BigInteger memUsageInBytes;
        private BigInteger memMaxUsageInBytes;
        private BigInteger memoryLimitInBbytes;
        private BigInteger memoryFailcnt;

        public String getContainerID() {
            return containerID;
        }

        public void setContainerID(String containerID) {
            this.containerID = containerID;
        }

        public BigInteger getCache() {
            return cache;
        }

        public void setCache(BigInteger cache) {
            this.cache = cache;
        }

        public BigInteger getRss() {
            return rss;
        }

        public void setRss(BigInteger rss) {
            this.rss = rss;
        }

        public BigInteger getRssHuge() {
            return rssHuge;
        }

        public void setRssHuge(BigInteger rssHuge) {
            this.rssHuge = rssHuge;
        }

        public BigInteger getMappedFile() {
            return mappedFile;
        }

        public void setMappedFile(BigInteger mappedFile) {
            this.mappedFile = mappedFile;
        }

        public BigInteger getPgpgin() {
            return pgpgin;
        }

        public void setPgpgin(BigInteger pgpgin) {
            this.pgpgin = pgpgin;
        }

        public BigInteger getPgpgout() {
            return pgpgout;
        }

        public void setPgpgout(BigInteger pgpgout) {
            this.pgpgout = pgpgout;
        }

        public BigInteger getPgfault() {
            return pgfault;
        }

        public void setPgfault(BigInteger pgfault) {
            this.pgfault = pgfault;
        }

        public BigInteger getPgmajfault() {
            return pgmajfault;
        }

        public void setPgmajfault(BigInteger pgmajfault) {
            this.pgmajfault = pgmajfault;
        }

        public BigInteger getInactiveAnon() {
            return inactiveAnon;
        }

        public void setInactiveAnon(BigInteger inactiveAnon) {
            this.inactiveAnon = inactiveAnon;
        }

        public BigInteger getActiveAnon() {
            return activeAnon;
        }

        public void setActiveAnon(BigInteger activeAnon) {
            this.activeAnon = activeAnon;
        }

        public BigInteger getInactiveFile() {
            return inactiveFile;
        }

        public void setInactiveFile(BigInteger inactiveFile) {
            this.inactiveFile = inactiveFile;
        }

        public BigInteger getActiveFile() {
            return activeFile;
        }

        public void setActiveFile(BigInteger activeFile) {
            this.activeFile = activeFile;
        }

        public BigInteger getUnevictable() {
            return unevictable;
        }

        public void setUnevictable(BigInteger unevictable) {
            this.unevictable = unevictable;
        }

        public BigInteger getHierarchicalMemoryLimit() {
            return hierarchicalMemoryLimit;
        }

        public void setHierarchicalMemoryLimit(BigInteger hierarchicalMemoryLimit) {
            this.hierarchicalMemoryLimit = hierarchicalMemoryLimit;
        }

        public BigInteger getTotalCache() {
            return totalCache;
        }

        public void setTotalCache(BigInteger totalCache) {
            this.totalCache = totalCache;
        }

        public BigInteger getTotalRss() {
            return totalRss;
        }

        public void setTotalRss(BigInteger totalRss) {
            this.totalRss = totalRss;
        }

        public BigInteger getTotalRssHuge() {
            return totalRssHuge;
        }

        public void setTotalRssHuge(BigInteger totalRssHuge) {
            this.totalRssHuge = totalRssHuge;
        }

        public BigInteger getTotalMappedFile() {
            return totalMappedFile;
        }

        public void setTotalMappedFile(BigInteger totalMappedFile) {
            this.totalMappedFile = totalMappedFile;
        }

        public BigInteger getTotalPgpgin() {
            return totalPgpgin;
        }

        public void setTotalPgpgin(BigInteger totalPgpgin) {
            this.totalPgpgin = totalPgpgin;
        }

        public BigInteger getTotalPgpgout() {
            return totalPgpgout;
        }

        public void setTotalPgpgout(BigInteger totalPgpgout) {
            this.totalPgpgout = totalPgpgout;
        }

        public BigInteger getTotalPgfault() {
            return totalPgfault;
        }

        public void setTotalPgfault(BigInteger totalPgfault) {
            this.totalPgfault = totalPgfault;
        }

        public BigInteger getTotalPgmajfault() {
            return totalPgmajfault;
        }

        public void setTotalPgmajfault(BigInteger totalPgmajfault) {
            this.totalPgmajfault = totalPgmajfault;
        }

        public BigInteger getTotalInactiveAnon() {
            return totalInactiveAnon;
        }

        public void setTotalInactiveAnon(BigInteger totalInactiveAnon) {
            this.totalInactiveAnon = totalInactiveAnon;
        }

        public BigInteger getTotalActiveAnon() {
            return totalActiveAnon;
        }

        public void setTotalActiveAnon(BigInteger totalActiveAnon) {
            this.totalActiveAnon = totalActiveAnon;
        }

        public BigInteger getTotalInactiveFile() {
            return totalInactiveFile;
        }

        public void setTotalInactiveFile(BigInteger totalInactiveFile) {
            this.totalInactiveFile = totalInactiveFile;
        }

        public BigInteger getTotalActiveFile() {
            return totalActiveFile;
        }

        public void setTotalActiveFile(BigInteger totalActiveFile) {
            this.totalActiveFile = totalActiveFile;
        }

        public BigInteger getTotalUnevictable() {
            return totalUnevictable;
        }

        public void setTotalUnevictable(BigInteger totalUnevictable) {
            this.totalUnevictable = totalUnevictable;
        }

        public BigInteger getMemUsageInBytes() {
            return memUsageInBytes;
        }

        public void setMemUsageInBytes(BigInteger memUsageInBytes) {
            this.memUsageInBytes = memUsageInBytes;
        }

        public BigInteger getMemMaxUsageInBytes() {
            return memMaxUsageInBytes;
        }

        public void setMemMaxUsageInBytes(BigInteger memMaxUsageInBytes) {
            this.memMaxUsageInBytes = memMaxUsageInBytes;
        }

        public BigInteger getMemoryLimitInBbytes() {
            return memoryLimitInBbytes;
        }

        public void setMemoryLimitInBbytes(BigInteger memoryLimitInBbytes) {
            this.memoryLimitInBbytes = memoryLimitInBbytes;
        }

        public BigInteger getMemoryFailcnt() {
            return memoryFailcnt;
        }

        public void setMemoryFailcnt(BigInteger memoryFailcnt) {
            this.memoryFailcnt = memoryFailcnt;
        }
    }

    public static class CpuInfoBean {
        /**
         * cpu : 6881f4d7d3f29b82932addf43916800dca8e7f7ec83eb744112975fdec99863d
         * user : 0.03
         * system : 0.06
         * idle : 0
         * nice : 0
         * iowait : 0
         * irq : 0
         * softirq : 0
         * steal : 0
         * guest : 0
         * guestNice : 0
         */

        private String cpu;
        private double user;
        private double system;
        private BigInteger idle;
        private BigInteger nice;
        private BigInteger iowait;
        private BigInteger irq;
        private BigInteger softirq;
        private BigInteger steal;
        private BigInteger guest;
        private BigInteger guestNice;

        public String getCpu() {
            return cpu;
        }

        public void setCpu(String cpu) {
            this.cpu = cpu;
        }

        public double getUser() {
            return user;
        }

        public void setUser(double user) {
            this.user = user;
        }

        public double getSystem() {
            return system;
        }

        public void setSystem(double system) {
            this.system = system;
        }

        public BigInteger getIdle() {
            return idle;
        }

        public void setIdle(BigInteger idle) {
            this.idle = idle;
        }

        public BigInteger getNice() {
            return nice;
        }

        public void setNice(BigInteger nice) {
            this.nice = nice;
        }

        public BigInteger getIowait() {
            return iowait;
        }

        public void setIowait(BigInteger iowait) {
            this.iowait = iowait;
        }

        public BigInteger getIrq() {
            return irq;
        }

        public void setIrq(BigInteger irq) {
            this.irq = irq;
        }

        public BigInteger getSoftirq() {
            return softirq;
        }

        public void setSoftirq(BigInteger softirq) {
            this.softirq = softirq;
        }

        public BigInteger getSteal() {
            return steal;
        }

        public void setSteal(BigInteger steal) {
            this.steal = steal;
        }

        public BigInteger getGuest() {
            return guest;
        }

        public void setGuest(BigInteger guest) {
            this.guest = guest;
        }

        public BigInteger getGuestNice() {
            return guestNice;
        }

        public void setGuestNice(BigInteger guestNice) {
            this.guestNice = guestNice;
        }
    }
}
