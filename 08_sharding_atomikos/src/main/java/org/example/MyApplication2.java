package org.example;

import org.example.mapper.OrderMapper;
import org.example.model.OrderEntity;
import org.example.model.UpdateRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@MapperScan("org.example.mapper")
@SpringBootApplication
public class MyApplication2 {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication2.class, args);
    }

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

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

    @PutMapping("/order/xa")
    public int update(@RequestBody UpdateRequest request) {
        return orderService.update(request.getInfos());
    }
}
