package xpu.ctrl.docker.service;

import xpu.ctrl.docker.entity.HostEntity;
import xpu.ctrl.docker.vo.HostEntityVO;
import xpu.ctrl.docker.vo.HostRunningVO;
import xpu.ctrl.docker.vo.HostRunningVOArray;

import java.util.List;
import java.util.Map;

public interface HostEntityService {
    HostEntity queryById(String hostIp);

    List<HostEntityVO> getAllHost();

    List<HostRunningVO> getRunningHost();

    List<HostEntityVO> getRunningHostByLoad(int num);
}