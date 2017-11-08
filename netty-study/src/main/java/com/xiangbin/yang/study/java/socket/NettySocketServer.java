package com.xiangbin.yang.study.java.socket;

import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangbin.yang
 * @since 2017/11/8
 */
@Slf4j
public class NettySocketServer {
    public static void main(String[] args) {
        NettySocketServer server = new NettySocketServer();
        server.start(8082);
    }

    public void start(int port) {
        log.info("Start");
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
            .group(loopGroup)
            //.option(ChannelOption.TCP_NODELAY, true)
            //.option(ChannelOption.SO_RCVBUF, Integer.MAX_VALUE)
            //.option(ChannelOption.SO_KEEPALIVE, true)
            .channel(NioServerSocketChannel.class)
            //.handler(new LoggingHandler(LogLevel.DEBUG))
            .childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    //ch.pipeline().addLast("split", new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer("===", Charset.forName("utf-8"))));
                    log.info("Initialize handler pipeline for: {}", ch);
                    ch.pipeline().addLast("lineBaseSplit", new LineBasedFrameDecoder(Integer.MAX_VALUE));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast("echo", new EchoHandler2());
                }
            });

        try {
            System.out.println("Start netty server at: " + port);
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            loopGroup.shutdownGracefully();
        }
    }
}
