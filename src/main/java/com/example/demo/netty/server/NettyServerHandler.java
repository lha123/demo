package com.example.demo.netty.server;

import com.alibaba.fastjson.JSON;
import com.example.demo.netty.Request;
import com.example.demo.netty.Response;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息处理器
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;

        log.info("=========》》》Client Data:" + JSON.toJSONString(request));

        Response response = new Response();
        response.setRequestId(request.getRequestId());
        response.setResult("Hello Client !");

        // client接收到信息后主动关闭掉连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
