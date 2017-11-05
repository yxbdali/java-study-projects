package com.xiangbin.yang.study.java.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeEchoHander extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        //System.out.println("Receive client request!");
        System.out.println(msg.method().name() + " " + msg.uri() + " " + msg.protocolVersion());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(DATEFORMAT.format(new Date()).getBytes("utf-8"));

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
