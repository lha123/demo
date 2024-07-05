package com.example.demo.netty.Mina;

import lombok.SneakyThrows;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MinaServer {

    private static final Logger log = LoggerFactory.getLogger(MinaServer.class);

    public static void main(String[] args) {
        SocketAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

        ProtocolCodecFilter filter = new ProtocolCodecFilter(new LnContextCodecFactory());
        // 添加编码过滤器 处理乱码问题、编码问题
        chain.addLast("codec", filter);

        // 打印Mina内部的流程日志
        chain.addLast("logger", new LoggingFilter());

        // 并发提升服务端处理session的性能
        chain.addLast("threadPool",
                new ExecutorFilter(Executors.newFixedThreadPool(5)));

        // 设置核心消息业务处理器
        acceptor.setHandler(new MinaServerHandler());

        // 设置读的session的空闲时间
        acceptor.getSessionConfig().setReaderIdleTime(100);

        // 绑定端口
        try {
            acceptor.bind(new InetSocketAddress(5005));
            log.info("mina监听:{}",5005);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}
