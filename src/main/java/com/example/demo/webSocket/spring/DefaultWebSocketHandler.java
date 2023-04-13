package com.example.demo.webSocket.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.*;

public class DefaultWebSocketHandler implements WebSocketHandler {

    @Autowired
    private SpringWebSocket springWebSocket;

    /**
     * 建立连接
     *
     * @param session Session
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        springWebSocket.handleOpen(session);
    }

    /**
     * 接收消息
     *
     * @param session Session
     * @param message 消息
     */
    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            springWebSocket.handleMessage(session, textMessage.getPayload());
        }
    }

    /**
     * 发生错误
     *
     * @param session   Session
     * @param exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        springWebSocket.handleError(session, exception);
    }

    /**
     * 关闭连接
     *
     * @param session     Session
     * @param closeStatus 关闭状态
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) {
        springWebSocket.handleClose(session);
    }

    /**
     * 是否支持发送部分消息
     *
     * @return false
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

