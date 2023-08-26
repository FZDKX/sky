package com.fzdkx.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2023/8/26 11:13
 */
@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class MyWebSocketServer {

    private static final Map<String,Session> map = new HashMap<>();

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session , @PathParam("sid") String sid){
        log.info("正在与客户端：{} 建立连接",sid);
        map.put(sid,session);
    }

    /**
     * 接收客户端消息
     */
    @OnMessage
    public void onMessage(String message , @PathParam("sid") String sid){
        log.info("客户端：{} 发来消息 ：{}",sid,message);
    }

    /**
     * 与客户端断开连接
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid){
        log.info("与客户端：{} 断开连接",sid);
        map.remove(sid);
    }

    /**
     * 向客户端群发消息
     */
    public void sendToAllClient(String message){
        Collection<Session> sessions = map.values();
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
