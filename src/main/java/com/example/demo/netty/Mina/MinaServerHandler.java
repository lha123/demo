package com.example.demo.netty.Mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.Optional;

/**
 * mina服务端实现类
 * 仅实现云快充协议解析
 *
 * @author yuan dian
 * @date 2019/1/21
 * @time 14:32
 */
@Slf4j
@Component
@EnableScheduling
public class MinaServerHandler extends IoHandlerAdapter {


    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("sessionIdle");
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("sessionClosed");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("exceptionCaught");
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        long sessionId = session.getId();
        String clientIp = getHostIPAddress(session);
        if (!(message instanceof ContextProtocol)) {
            log.warn("非法协议内容：{}，sessionId为：{}，IP为：{}", message, sessionId, clientIp);
            return;
        }

        ContextProtocol protocol = (ContextProtocol) message;
        log.info("接收到报文，sessionId：{}，IP：{}，报文内容：{}", sessionId, clientIp, protocol.getHexString());

    }

    public static String getHostIPAddress(IoSession session) {
        SocketAddress remoteAddress = session.getRemoteAddress();
        return Optional.ofNullable(remoteAddress).map(SocketAddress::toString).orElse(null);
    }

}