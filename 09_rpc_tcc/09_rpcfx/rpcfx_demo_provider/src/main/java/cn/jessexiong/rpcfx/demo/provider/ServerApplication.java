package cn.jessexiong.rpcfx.demo.provider;

import cn.jessexiong.rpcfx.demo.api.*;
import cn.jessexiong.rpcfx.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Autowired
    RpcfxInvoker invoker;

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        return invoker.invoke(request);
    }

    @Bean
    public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver){
        return new RpcfxInvoker(resolver);
    }

    @Bean
    public RpcfxResolver createResolver(){
        return new DemoResolver();
    }


    @Bean(name = "cn.jessexiong.rpcfx.demo.api.UserService")
    public UserService createUserService() {
        return new UserServiceImpl();
    }

    @Bean(name = "cn.jessexiong.rpcfx.demo.api.OrderService:")
    public OrderService createOrderService() {
        return new OrderServiceImpl();
    }
}
