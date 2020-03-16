package xpu.ctrl.docker.service;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xpu.ctrl.docker.config.MySpringConfigurator;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Getter
@Component
@ServerEndpoint(value = "/getHostInfoWebSocket", configurator = MySpringConfigurator.class)
public class GetHostInfoWebSocket {
    private Session session;

    public static CopyOnWriteArrayList<GetHostInfoWebSocket> webSocketSet = Lists.newCopyOnWriteArrayList();

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("[WebSocket消息] 有新的连接，总数：{}", webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("[WebSocket消息] 连接断开，总数：{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("[WebSocket消息] 收到客户端的消息：{}", message);
    }

    public void sendMessage(String message){
        for(GetHostInfoWebSocket webSocket: webSocketSet) {
            log.info("[WebSocket消息] 广播消息：message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}