package cn.jessexiong.rpcfx.server;

import cn.jessexiong.rpcfx.demo.api.RpcfxRequest;
import cn.jessexiong.rpcfx.demo.api.RpcfxResolver;
import cn.jessexiong.rpcfx.demo.api.RpcfxResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest req) {
        RpcfxResponse resp = new RpcfxResponse();
        String serviceClass = req.getServiceClass();

        Object service = resolver.resolve(serviceClass);
        try {
            Method method = resolveMethodFromClass(service.getClass(), req.getMethod());
            Object result = method.invoke(service, req.getParams());
            resp.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            resp.setStatus(true);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            resp.setException(e);
            resp.setStatus(false);
        }
        return resp;
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}
