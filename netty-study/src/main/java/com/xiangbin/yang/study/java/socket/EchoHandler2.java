package com.xiangbin.yang.study.java.socket;

import java.nio.charset.Charset;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xiangbin.yang
 * @since 2017/11/8
 */
public class EchoHandler2 extends SimpleChannelInboundHandler<String> {
    int i = 1;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //for (ByteBuf msg : msgList) {
        //    System.out.println(msg.toString(Charset.forName("utf-8")));
        //}
        //ByteBuf byteBuf = (ByteBuf)msg;
        //System.out.println(byteBuf.toString(Charset.forName("utf-8")));
        System.out.println(i++ + ":" + msg);
        //ctx.write(Unpooled.copiedBuffer(msg, Charset.forName("utf-8")));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Echo from server!", Charset.forName("utf-8"))).addListener(ChannelFutureListener.CLOSE);
    }
}
