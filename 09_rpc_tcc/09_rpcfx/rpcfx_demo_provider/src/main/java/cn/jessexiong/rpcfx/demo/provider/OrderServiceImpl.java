package cn.jessexiong.rpcfx.demo.provider;

import cn.jessexiong.rpcfx.demo.api.Order;
import cn.jessexiong.rpcfx.demo.api.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public Order findOrderById(int id) {
        return new Order(id, "apple" + System.currentTimeMillis(), 9999);
    }
}
