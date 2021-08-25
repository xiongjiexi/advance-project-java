---
createTime: 6/26/2021, 01:54:34
updateTime: 6/26/2021, 01:59:55
timestamp: 1624643995150
---
# NIO和Socket入门

## BIO & NIO & AIO这三兄弟

### BIO(blocking IO)

> 同步并阻塞IO模型
>
> 一个请求对应一个线程


### NIO(new IO)

> 同步非阻塞
>
> 可能有多个请求连接对应一个线程, 但是服务端实现了一个`多路复用器`, 连接会包装为channel注册到多路复用器中并由它轮询, 当有请求时会让线程来处理.



### AIO(asynchronous IO)

> 异步非阻塞
>
> 一个请求对应一个线程, 请求过来都是由OS完成后再通知应用的线程去进行处理.


## Socket中的概念

平常大家古老编程方式是用ServerSocket去监听一个端口，通过阻塞调用accept()来获取`Socket clientSocket = serverSocket.accept();`对象。这种方式实际上是使用本地系统套接字库提供的阻塞函数。

套接字是传输层提供的接口，Java中提供的Socket是对其的抽象，并封装了下层的细节。

Java中有3种socket：

1. java.net.Socket（TCP）
2. java.net.ServerSocket（TCP）
3. DatagramSocket（UDP）

第一、二种是建立在TCP基础上，而第三种是建立在UDP基础上。

连接双方可以获取远程（对方）的ip地址和端口。

socket正常使用需要配置很多参数去适应使用场景。

## Socket使用



### 先简单编写一个单线程SocketServer和SocketClient

**Server:**

此时服务端还只能连接一个客户端，属于单线程模式

```java
public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        ServerSocketTest serverTest = new ServerSocketTest();
        serverTest.server(8081);
    }

    private void server(int port) throws IOException {
        // 向操作系统注册服务
        ServerSocket serverSocket = new ServerSocket(port);
        // accept会阻塞到一个连接建立（等待客户端连接），建立之后，就可以继续监听传入的连接了。
        Socket clientSocket = serverSocket.accept();
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

    private String process(String request) {
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
```

server多线程模式：

```java
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
```





**client:**

```java
public class ClientSocketTest {
    public static void main(String[] args) throws IOException {
        ClientSocketTest test = new ClientSocketTest();
        test.clientServer("localhost", 8081);
    }

    private void clientServer(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
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
```



### 客户端socket连接服务端时如何设置超时，默认不会超时

```java
Socket socket = new Socket();
SocketAddress remoteAddr = new InetSocketAddress(host, port);
// 设定一分钟的超时时间
socket.connect(remoteAddr, 60000);
```



### 在客户端程序中，设定客户端地址

你可以指定ip地址，因为在某些情况下，一个主机可能有两个ip地址，此时可以显式指定

```java
InetAddress remoteAddress = InetAddress.getByName("127.0.0.1");
InetAddress localAddress = InetAddress.getByName("127.0.0.1");
// 指定客户端地址和端口
Socket socket = new Socket(remoteAddress, 8081, localAddress, 2345);
```



### 客户端使用代理服务器连接

```java
String proxyIp = "myProxy.abc.com";
int proxyPort = 18080;
Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyIp, proxyPort));
// 设定proxy
Socket socket = new Socket(proxy);
// 设定remote地址和端口
SocketAddress remoteAddr = new InetSocketAddress(host, port);
socket.connect(remoteAddr, 60000);
```



### 通过InetAddress获取主机名

1. getHostname()先从DNS缓存中查找，没有再去DNS服务器查找
2. getCanonicalHostName()直接从DNS服务器查找



### socket选项、参数

- TCP_NODELAY：表示立即发送数据。
- SO_RESUSEADDR：表示是否允许重用Socket所绑定的本地地址。
- SO_TIMEOUT：表示接收数据时的等待超时时间。
- SO_LINGER：表示当执行Socket的close()方法时，是否立即关闭底层的Socket。
- SO_SNDBUF：表示发送数据的缓冲区的大小。
- SO_RCVBUF：表示接收数据的缓冲区的大小。
- SO_KEEPALIVE：表示对于长时间处于空闲状态的Socket，是否要自动把它关闭。
- OOBINLINE：表示是否支持发送1字节的TCP紧急数据。



### 设定客户连接请求队列的长度

管理客户连接请求的工作是操作系统来完成，一个客户端连接上了服务端的对应端口，操作系统会将其放入到一个指定长度的队列中（默认50），当队列中请求达到最大长度，则会拒绝新的请求连接，直到通过`ServerSocket.accept()`从队列取出请求连接，队列才能继续加入新的请求连接。

我们可以自定义队列长度：

```java
// 请求连接的队列长度为3
ServerSocket server = new ServerSocket(port, 3); 
```



## NIO

