package com.xiangbin.yang.study.java.socket;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xiangbin.yang
 * @since 2017/11/8
 */
public class EchoHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

            String info = msg.toString(Charset.forName("utf-8"));
            System.out.println(info);

        ctx.writeAndFlush(Unpooled.copiedBuffer("Echo from server!", Charset.forName("utf-8"))).addListener(ChannelFutureListener.CLOSE);
    }
}
