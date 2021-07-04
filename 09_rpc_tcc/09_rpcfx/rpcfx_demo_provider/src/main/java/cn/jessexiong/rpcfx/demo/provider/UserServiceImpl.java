package cn.jessexiong.rpcfx.demo.provider;

import cn.jessexiong.rpcfx.demo.api.User;
import cn.jessexiong.rpcfx.demo.api.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User findById(int id) {
        return new User(id, "jesse" + System.currentTimeMillis());
    }
}
