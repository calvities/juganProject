package com.jg.project.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName RedisBean
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 17:11
 * @Version 1.0
 **/
@SpringBootConfiguration
public class RedisBean {

    @Value("${jg.redis.host}")
    private String host;

    @Value("${jg.redis.port}")
    private Integer port;

    @Value("${jg.redis.password}")
    private String password;

    @Value("${jg.pool.max.wait}")
    private Integer wait;

    @Value("${jg.pool.max.idle}")
    private Integer maxIdle;

    @Value("${jg.pool.max.total}")
    private Integer maxTotal;

    @Value("${jg.pool.timeout}")
    private Integer timeout;

    @Bean
    public JedisPool getJedisPoolConfig() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setMaxWaitMillis(wait);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        return new JedisPool(config, host, port, timeout, password);
    }
}
