package com.xiangbin.yang.study.java.socket;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import static java.lang.System.out;

/**
 * @author xiangbin.yang
 * @since 2017/11/8
 */
public class SocketServer {
    public static final ExecutorService POOL = Executors.newFixedThreadPool(8);

    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        out.println("Server socket start at: " + port);
        for (;;) {
            Socket client = serverSocket.accept();
            POOL.execute(() -> {
                out.println("Accept client request from: " + client.getInetAddress());
                try {
                    try (InputStream in = client.getInputStream(); PrintWriter writer = new PrintWriter(client.getOutputStream()))
                    {
                        String line = null;
                        boolean lastBlank = false;

                        ByteArrayOutputStream bao = new ByteArrayOutputStream();

                        while (true) {
                            int d = in.read();
                            if (d == -1)
                                break;;
                            bao.write(d);

                            if (in.available() == 0) {
                                break;
                            }
                        }
                        out.print(bao.toString("utf-8"));
                        bao.close();

                        writer.println("Echo from SocketServer!");
                        writer.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws IOException {
        SocketServer server = new SocketServer();
        server.start(83);
    }
}
