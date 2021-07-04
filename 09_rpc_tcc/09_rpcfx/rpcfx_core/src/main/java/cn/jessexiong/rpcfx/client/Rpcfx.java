package cn.jessexiong.rpcfx.client;

import cn.jessexiong.rpcfx.demo.api.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("cn.jessexiong.rpcfx");
    }

    public static <T, filters> T createFromRegistry(final Class<T> serviceClass,
                                                    final String zkUrl,
                                                    Router router,
                                                    LoadBalancer loadBalancer,
                                                    Filter filter) {
        List<String> invokers = new ArrayList<>();

        List<String> urls = router.route(invokers);

        String url = loadBalancer.select(urls);

        return (T) create(serviceClass, url, filter);
    }

    public static <T> T create(Class<T> serviceClass, String url, Filter... filters) {
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(),
                new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url, filters));
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
