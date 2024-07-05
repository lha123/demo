package com.example.demo.netty.Mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

@Slf4j
public class ClientHandler extends IoHandlerAdapter {

    public ClientHandler() {
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("我收到回复了");

    }
}
