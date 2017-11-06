package com.xiangbin.yang.study.java.littleproxy;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.junit.Test;

/**
 * @author xiangbin.yang
 * @since 2017/11/6
 */
public class LittleProxyTest {
    @Test
    public void testLittleProxy() throws IOException {
        int port = 83;
        ProxyServer proxyServer = new ProxyServer();
        proxyServer.start(port);

        HttpHost proxy = new HttpHost("localhost", port);

        Response response = Request.Get("http://www.baidu.com").viaProxy(proxy).execute();
        System.out.println(response.returnContent().asString());
    }
}
