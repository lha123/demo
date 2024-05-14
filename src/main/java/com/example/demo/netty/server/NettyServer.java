package com.example.demo.netty.server;


import com.example.demo.netty.Request;
import com.example.demo.netty.Response;
import com.example.demo.netty.RpcDecoder;
import com.example.demo.netty.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * netty的服务端
 */
public class NettyServer {

    private String ip;
    private int port;

    public NettyServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void server() throws Exception {

        /**
         * Server端的EventLoopGroup分为两个
         * workerGroup作为处理请求，
         * bossGroup作为接收请求。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            /**
             * ChannelOption四个常量作为TCP连接中的属性。
             *
             *
             */
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
//                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
//                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RpcDecoder(Request.class))
                                    .addLast(new RpcEncoder(Response.class));

                            //注册消息处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());
//                                    .addLast(new NettyServerHandler());
                        }
                    });

            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);  // 开启长连接

            ChannelFuture future = serverBootstrap.bind(ip, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyServer("127.0.0.1", 20000).server();
    }
}
