package com.example.demo.netty.Mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;


@Slf4j
@RestController
@RequestMapping("/send")
public class MinaClient {

    @PostMapping("/show")
    public void show(@RequestBody Object[] objects){
        IoSession session = MinaSocketUtil.getSession("23090601027812");
        ByteBuffer out = ByteBuffer.allocate(1024);
        out.put(Utils.int2byte(104));
        for (Object obj : objects) {
            if(obj instanceof Integer){
                out.put(Utils.int2byte(((Integer) obj).intValue()));
            }
            if(obj instanceof String){
                out.put(Utils.hexStr2Bytes(obj.toString(),false));
            }
        }
        out.put(Utils.calcCrc16(out));
        out.flip();
        byte[] octets = new byte[out.remaining()];
        out.get(octets);
        IoBuffer o = IoBuffer.wrap(octets);
        session.write(o);
    }


    @PostConstruct
    public void inti(){
        initMinaClientAndLogin("23090601027812",1);
    }

    public static void initMinaClientAndLogin(String pileCode, int gunAmount) {
        try {
            SocketConnector connector = new NioSocketConnector();
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new LnContextCodecFactory()));
            connector.setConnectTimeoutMillis(30000L);
            connector.setHandler(new ClientHandler());
            ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 8767));
            future.awaitUninterruptibly();
            if (future.isConnected()) {
                log.info("session已连接，桩登录：{}", pileCode);
                IoSession session = future.getSession();
                ByteBuffer out = ByteBuffer.allocate(1024);
                out.put(Utils.int2byte(104));
                out.put(Utils.int2byte(34));
                out.put(Utils.hexStr2Bytes("0000", false));
                out.put(Utils.int2byte(0));
                out.put(Utils.int2byte(1));
                out.put(Utils.hexStr2Bytes(pileCode, false));
                out.put(Utils.int2byte(0));
                out.put(Utils.int2byte(gunAmount));
                out.put(Utils.int2byte(16));
                out.put(Utils.hexStr2Bytes("56342E312E353000", false));
                out.put(Utils.int2byte(1));
                out.put(Utils.hexStr2Bytes("01010101010101010101", false));
                out.put(Utils.int2byte(4));
                out.put(Utils.calcCrc16(out));
                out.flip();
                byte[] octets = new byte[out.remaining()];
                out.get(octets);
                IoBuffer o = IoBuffer.wrap(octets);
                session.write(o);
                session.setAttribute("pileCode", pileCode);
                session.setAttribute("gunCount", gunAmount);
                MinaSocketUtil.cacheSession(pileCode, session);
                String strHex = Utils.bytesHexString(octets, 0, octets.length, false);
                log.info("登录成功报文{}", strHex);
            } else {
                log.info("session is timeout pileCode:{}", pileCode);
            }
        } catch (Exception var10) {
            Exception e = var10;
            log.error("链接失败", e);
        }
    }
}
