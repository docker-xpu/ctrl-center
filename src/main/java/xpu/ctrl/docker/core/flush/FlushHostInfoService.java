package xpu.ctrl.docker.core.flush;

public interface FlushHostInfoService {

    void flushHostInfoToCatch(String ip);

    void flushHostInfoToStore();
}
