package com.xiangbin.yang.study.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.System.out;

/**
 * @author xiangbin.yang
 * @since 2017/11/8
 */
public class SocketClient {
    public void start(String host, int port) throws IOException {
        Socket client = new Socket(host, port);
        try (PrintWriter writer = new PrintWriter(client.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"))) {
            writer.println("Hello echo from client!");
            writer.flush();
            //client.shutdownOutput();

            String line = null;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        }
        client.close();
    }

    public static void main(String[] args) throws IOException {
        SocketClient client = new SocketClient();
        client.start("localhost", 83);
    }
}
