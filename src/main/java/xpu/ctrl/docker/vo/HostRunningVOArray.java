package xpu.ctrl.docker.vo;

import lombok.Data;

import java.awt.*;
import java.util.Map;

@Data
public class HostRunningVOArray {
    Map<String, List> map;
}