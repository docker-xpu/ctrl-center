package xpu.ctrl.docker.core.util;

import com.alibaba.fastjson.JSONObject;
import xpu.ctrl.docker.core.flush.HostEntityHardWare;
import xpu.ctrl.docker.util.KeyUtil;

public class HostInfoToObjectUtil {
    public static HostEntityHardWare jsonToHostEntityHardWare(JSONObject jsonObject){
        HostEntityHardWare hardWare = new HostEntityHardWare();
        hardWare.setUpdateTime(KeyUtil.genUniqueKey());
        hardWare.setIp(null);
        hardWare.setCpuInfo(jsonObject.getString("cpu_info"));
        hardWare.setDiskInfo(jsonObject.getString("disk_info"));
        hardWare.setHostInfo(jsonObject.getString("host_info"));
        hardWare.setIoCounters(jsonObject.getString("io_counters"));
        hardWare.setLoadInfo(jsonObject.getString("load_info"));
        hardWare.setMemInfo(jsonObject.getString("mem_info"));
        hardWare.setProcess(jsonObject.getString("process"));
        hardWare.setProtoCounters(jsonObject.getString("proto_counters"));
        hardWare.setSensors(jsonObject.getString("sensors"));
        return hardWare;
    }
}
