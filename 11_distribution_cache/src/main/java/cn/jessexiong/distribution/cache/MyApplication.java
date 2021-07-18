package cn.jessexiong.distribution.cache;

import cn.jessexiong.distribution.cache.model.BatchUpdateRequest;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@SpringBootApplication
@MapperScan("cn.jessexiong.distribution.cache.mapper")
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    @Qualifier("jedis")
    private Jedis jedis;

    public static final String INVENTORY_KEY = "inventory_key";

    public static final String BATCH_UPDATE_CHANNEL = "batch_update_channel";

    /**
     * 实现分布式锁，模拟分布式锁调用
     * @param lockTime lockTime
     * @return String
     */
    @SneakyThrows
    @GetMapping("/")
    public String getOrder(@RequestParam(defaultValue = "0") int lockTime) {
        String key = "jesse";
        String lockId = "empty";

        try {
            lockId = redisOperation.lock(key, 10_000);
            if (lockId == null) {
                System.out.println("failure");
            } else {
                System.out.println("success:"+ lockId);
            }

            TimeUnit.SECONDS.sleep(lockTime);
        } finally {
            redisOperation.unlock(key, lockId);
        }

        return "hello";
    }

    /**
     * 在 Java 中实现一个分布式计数器，模拟减库存。(1)
     *
     * @param num num
     * @return String
     */
    @GetMapping("/in")
    public String in_inventory(@RequestParam(defaultValue = "0") int num) {
        jedis.incrBy(INVENTORY_KEY, num);
        return "in success";
    }

    /**
     * 在 Java 中实现一个分布式计数器，模拟减库存。(2)
     *
     * @param num num
     * @return String
     */
    @GetMapping("/out")
    public String out_inventory(@RequestParam(defaultValue = "0") int num) {
        jedis.decrBy(INVENTORY_KEY, num);
        return "out success";
    }

    @PostMapping("/update")
    public String batchUpdate(@RequestBody BatchUpdateRequest request) {
        String amount = request.getAmount();
        List<String> orderIds = request.getOrderIds();

        for (String orderId : orderIds) {
            JSONObject json = new JSONObject();
            json.put("amount", amount);
            json.put("orderId", orderId);
            jedis.publish(BATCH_UPDATE_CHANNEL, json.toJSONString());
        }
        return "batch update success";
    }
}
