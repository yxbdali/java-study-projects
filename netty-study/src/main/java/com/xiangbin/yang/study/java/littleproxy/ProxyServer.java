package com.xiangbin.yang.study.java.littleproxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 * @author xiangbin.yang
 * @since 2017/11/6
 */
public class ProxyServer {
    public void start(int port) {
        System.out.println("Start proxy server at: " + port);
        DefaultHttpProxyServer.bootstrap().withPort(port)
            .withFiltersSource(new HttpFiltersSourceAdapter() {
                @Override
                public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                    return new HttpFiltersAdapter(originalRequest, ctx) {
                        @Override
                        public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                            FullHttpRequest httpRequest = (FullHttpRequest)httpObject;
                            System.out.println(httpRequest);
                            return null;
                        }

                        @Override
                        public HttpObject serverToProxyResponse(HttpObject httpObject) {
                            FullHttpResponse httpResponse = (FullHttpResponse)httpObject;
                            System.out.println(httpResponse);
                            return httpObject;
                        }
                    };
                }

                @Override
                public int getMaximumRequestBufferSizeInBytes() {
                    return 5 * 1024 * 1024;
                }

                @Override
                public int getMaximumResponseBufferSizeInBytes() {
                    return 5 * 1024 * 1024;
                }
            }).start();
    }
}
