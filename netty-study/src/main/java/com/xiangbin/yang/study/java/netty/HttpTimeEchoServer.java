package com.xiangbin.yang.study.java.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpTimeEchoServer {
    private int port;

    public HttpTimeEchoServer(Integer port) {
        this.port = port == null ? 80 : port;
    }

    public void start() {
        NioEventLoopGroup accpetGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(accpetGroup, workGroup)
                .localAddress(port)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new HttpRequestDecoder());
                        ch.pipeline().addLast(new HttpObjectAggregator(1024));
                        ch.pipeline().addLast(new HttpContentCompressor());
                        ch.pipeline().addLast(new HttpResponseEncoder());
                        ch.pipeline().addLast(new ChunkedWriteHandler());
                        ch.pipeline().addLast(new TimeEchoHander());
                    }
                });

        try {
            bootstrap.bind().sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            accpetGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = args.length >= 1 ? Integer.parseInt(args[0]) : 8081;
        System.out.println("Start netty time echo server at: " + port);
        HttpTimeEchoServer server = new HttpTimeEchoServer(port);
        server.start();
    }
}
