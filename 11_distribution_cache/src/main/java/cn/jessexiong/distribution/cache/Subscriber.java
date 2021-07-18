package cn.jessexiong.distribution.cache;

import cn.jessexiong.distribution.cache.mapper.OrderMapper;
import cn.jessexiong.distribution.cache.model.OrderEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import java.math.BigDecimal;

@Slf4j
@Component
public class Subscriber extends JedisPubSub {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void onMessage(String channel, String message) {
        log.info(String.format("receive redis published message, channel %s, message %s", channel, message));

        JSONObject json = JSON.parseObject(message);
        OrderEntity entity = new OrderEntity();
        entity.setId(Long.parseLong((String)json.get("orderId")));
        entity.setAmount(new BigDecimal((String) json.get("amount")));
        orderMapper.update(entity);

        log.info("update success");
    }
}
