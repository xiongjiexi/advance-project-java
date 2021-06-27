package org.example;


import org.example.mapper.OrderMapper;
import org.example.mapper.UserMapper;
import org.example.model.OrderEntity;
import org.example.model.UserEntity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@EnableAutoConfiguration
@MapperScan("org.example.mapper")
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;


    @RequestMapping("/")
    String home() {
        return "hello world!";
    }

    @GetMapping("/user/list")
    public List<UserEntity> getUsers() {
        return userMapper.getAll();
    }

    @GetMapping("/order/list")
    public List<OrderEntity> getOrder() {
        return orderMapper.getAll();
    }

    @GetMapping("/order")
    public OrderEntity getOne(@RequestParam long id) {
        return orderMapper.getOne(id);
    }

    @PostMapping("/order")
    public int insert(@RequestBody OrderEntity order) {
        return orderMapper.insert(order);
    }

    @DeleteMapping("/order")
    public int delete(@RequestParam long id) {
        return orderMapper.delete(id);
    }

    @PutMapping("/order")
    public int update(@RequestBody OrderEntity order) {
        return orderMapper.update(order);
    }
}
