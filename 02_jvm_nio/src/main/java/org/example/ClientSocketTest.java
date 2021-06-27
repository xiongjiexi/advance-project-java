package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ClientSocketTest {
    public static void main(String[] args) throws IOException {
        ClientSocketTest test = new ClientSocketTest();
        test.clientServer("localhost", 8081);
    }

    private void clientServer(String host, int port) throws IOException {
        String proxyIp = "myProxy.abc.com";
        int proxyPort = 18080;
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyIp, proxyPort));
        Socket socket = new Socket(proxy);
        SocketAddress remoteAddr = new InetSocketAddress(host, port);
        socket.connect(remoteAddr, 60000);
        try {
            // 通过br、pw封装套接字的流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            // 获取本地控制台输入的流
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            // 读取本地控制台的输入
            while ((msg = localReader.readLine()) != null) {
                // 写入到socket的输出流，发送给服务端
                pw.println(msg);
                // 获取socket返回的响应数据，并打印
                System.out.println(br.readLine());
                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭socket
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
