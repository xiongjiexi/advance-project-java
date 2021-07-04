package cn.jessexiong.rpcfx.demo.consumer;

import cn.jessexiong.rpcfx.client.Rpcfx;
import cn.jessexiong.rpcfx.demo.api.User;
import cn.jessexiong.rpcfx.demo.api.UserService;

//@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        System.out.println("find user id = 1 from server :"+ user.getName());
    }
}
