package cn.jessexiong.rpcfx.client;

import cn.jessexiong.rpcfx.demo.api.Filter;
import cn.jessexiong.rpcfx.demo.api.RpcfxRequest;
import cn.jessexiong.rpcfx.demo.api.RpcfxResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;

public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("cn.jessexiong.rpcfx");
    }

//    public static <T, filters> T createFromRegistry(final Class<T> serviceClass,
//                                                    final String zkUrl,
//                                                    Router router,
//                                                    LoadBalancer loadBalancer,
//                                                    Filter filter) {
//        List<String> invokers = new ArrayList<>();
//
//        List<String> urls = router.route(invokers);
//
//        String url = loadBalancer.select(urls);
//
//        return (T) create(serviceClass, url, filter);
//    }

    public static <T> T create(Class<T> serviceClass, String url,String group, String version, Filter... filters) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setUseCache(false);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                RpcfxRequest req = new RpcfxRequest();
                req.setServiceClass(serviceClass.getName());
                req.setMethod(method.getName());
                req.setParams(objects);
                if (group != null) {
                    req.setGroup(group);
                }
                if (version != null) {
                    req.setVersion(version);
                }

                if (filters != null) {
                    for (Filter filter : filters) {
                        if (!filter.filter(req)) {
                            return null;
                        }
                    }
                }
                RpcfxResponse resp;
                try {
                    resp = post(req, url);
                } catch (Exception e) {
                    resp = new RpcfxResponse();
                    resp.setResult(null);
                    resp.setException(e);
                    resp.setStatus(false);
                }
                return JSON.parse(resp.getResult().toString());
            }
        });
        return (T) enhancer.create();
    }


    private static RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel channel)
                                throws Exception {
                            channel.pipeline().addLast(new HttpClientCodec());
                            channel.pipeline().addLast(new HttpObjectAggregator(65536));
                            channel.pipeline().addLast(new HttpContentDecompressor());
                            channel.pipeline().addLast(new HttpClientHandler());
                        }
                    });
            URL u = new URL(url);
            ChannelFuture future = bootstrap.connect(u.getHost(), u.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            group.shutdownGracefully();
        }

        String reqJson = JSON.toJSONString(req);
        System.out.println("req json:" + reqJson);

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: " + respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

    public static class RpcfxInvocationHandler implements InvocationHandler {

        public static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;
        private final String url;
        private final Filter[] filters;

        public RpcfxInvocationHandler(Class<?> serviceClass, String url, Filter[] filters) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.filters = filters;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            RpcfxRequest req = new RpcfxRequest();
            req.setServiceClass(this.serviceClass.getName());
            req.setMethod(method.getName());
            req.setParams(params);

            if (filters != null) {
                for (Filter filter : filters) {
                    if (!filter.filter(req)) {
                        return null;
                    }
                }
            }

            RpcfxResponse resp = post(req, url);
            return JSON.parse(resp.getResult().toString());
        }

        private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
            String reqJson = JSON.toJSONString(req);
            System.out.println("req json:" + reqJson);

            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().url(url).post(RequestBody.create(JSON_TYPE, reqJson))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp json: " + respJson);
            return JSON.parseObject(respJson, RpcfxResponse.class);
        }
    }
}
