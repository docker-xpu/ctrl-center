package xpu.ctrl.docker.dao;

public class ByteCodeSimple {
    public void stackLeakByThread(){
        while (true){
            new Thread(() -> {
                while (true){

                }
            }).start();
        }
    }
}