package cn.jessexiong.distribution.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@Slf4j
public class StartService implements ApplicationRunner {
    @Autowired
    @Qualifier("jedis-2")
    private Jedis jedis;

    @Autowired
    private Subscriber subscriber;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start subscribe...");
        jedis.subscribe(subscriber, MyApplication.BATCH_UPDATE_CHANNEL);
    }
}
