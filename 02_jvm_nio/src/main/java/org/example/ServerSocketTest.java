package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        ServerSocketTest serverTest = new ServerSocketTest();
        serverTest.server(8081);
    }

    private void server(int port) throws IOException {
        // 向操作系统注册服务
        ServerSocket serverSocket = new ServerSocket(port);
        // 利用while改造成多线程模式，每次accept都能获取一个client的socket对象，再将其放入到新的线程中处理。
        while (true) {
            // accept会阻塞到一个连接建立（等待客户端连接），建立之后，就可以继续监听传入的连接了。
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    run(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void run(Socket clientSocket) throws IOException {
        // 用BufferedReader、PrintWriter来接套接字
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        // 阻塞读取request
        while ((request = in.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }
            // 处理请求，获取响应
            response = process(request);
            // 返回响应
            out.println(response);
        }
    }

    private static String process(String request) {
        String response;
        switch (request) {
            case "bye":
                response = "bye";
                break;
            default:
                System.out.println(request);
                response = "please go on..";
        }
        return response;
    }

}
