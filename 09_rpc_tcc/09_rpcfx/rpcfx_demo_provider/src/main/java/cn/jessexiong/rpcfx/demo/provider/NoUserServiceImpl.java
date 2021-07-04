package cn.jessexiong.rpcfx.demo.provider;

import cn.jessexiong.rpcfx.demo.api.User;
import cn.jessexiong.rpcfx.demo.api.UserService;

public class NoUserServiceImpl implements UserService {
    @Override
    public User findById(int id) {
        return new User(id, "no users; " + System.currentTimeMillis());
    }
}
