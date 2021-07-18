package cn.jessexiong.distribution.cache;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisOperation {

    @Autowired
    @Qualifier("jedis")
    private Jedis jedis;

    @SneakyThrows
    public String lock(String key, int timeout) {
        String lockId = UUID.randomUUID().toString();
        long endTime = System.currentTimeMillis() + timeout;
        long result = 0;
        while (System.currentTimeMillis() < endTime && (result = jedis.setnx(key, lockId)) != 1) {
            TimeUnit.MILLISECONDS.sleep(500);
        }

        if (result != 1) return null;
        return lockId;
    }


    public void unlock(String key, String lockId) {
        if (lockId.equals(jedis.get(key))) {
            jedis.del(key);
        }
    }

}
