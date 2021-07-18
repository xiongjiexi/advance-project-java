package cn.jessexiong.distribution.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisConfiguration {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.pool.max-idle}")
    private int maxIdle;

    @Value("${redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.block-when-exhausted}")
    private boolean blockWhenExhausted;


    @Bean
    public JedisPool redisPoolFactory() throws Exception {
        System.out.println("=============JedisPool注入成功！！redis地址：" + host + ":" + port);
        System.out.println("=============JedisPool注入成功！！password：" + password + "maxIdle:" + maxIdle + "max-wait:" + maxWaitMillis + "blockWhenExhausted:" + blockWhenExhausted);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        //redis设置了密码时使用，没密码使用该方式初始化连接池会失败
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);
        //redis没设置密码时使用
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }


    @Bean("jedis")
    public Jedis jedisFactory(JedisPool jedisPool) {
        return jedisPool.getResource();
    }

    @Bean("jedis-2")
    public Jedis jedisFactory2(JedisPool jedisPool) {
        return jedisPool.getResource();
    }

}
