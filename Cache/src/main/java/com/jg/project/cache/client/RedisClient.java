package com.jg.project.cache.client;

import com.alibaba.fastjson.JSON;
import com.jg.project.comm.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * @ClassName RedisClient
 * @Author huangGuoGang@qq.com
 * @Date 2019-06-26 17:13
 * @Version 1.0
 **/
@SpringBootConfiguration
@Slf4j
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Value("${jg.redis.pre.key}")
    private String preKey;

    /**
     * 默认过期时间(7天)
     */
    private final Integer KEY_DEFAULT_EXPIRE = 7 * 24 * 60 * 60;

    /**
     * 获取对象
     *
     * @param key
     * @param clazz
     * @param check
     * @param <T>
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz,Boolean check) {

        if (StringUtils.isBlank(key)) {
            return null;
        }

        if(check){
            key = preKey + key;
        }
        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            String v = redis.get(key);
            if (StringUtils.isNotBlank(key)) {
                return JSON.parseObject(v, clazz);
            }
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }

        return null;
    }

    /**
     * 获取字符串对象
     *
     * @param key
     * @param check
     * @return
     */
    public String getString(String key,Boolean check) {

        if (StringUtils.isBlank(key)) {
            return null;
        }

        if(check){
            key = preKey + key;
        }

        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            return redis.get(key);
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }

        return null;
    }

    /**
     * 添加字符串
     *
     * @param key
     * @param value
     */
    public void addString(String key, String value,Boolean check) {

        if (StringUtils.isBlank(key)) {
            return;
        }

        //添加字符串
        addString(key, value, KEY_DEFAULT_EXPIRE,check);
    }

    /**
     * 添加字符串
     *
     * @param key
     * @param value
     * @param expire
     * @param check
     */
    public void addString(String key, String value, Integer expire,Boolean check) {

        if (StringUtils.isBlank(key)) {
            return;
        }

        if(check){
            key = preKey + key;
        }

        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            //存值
            redis.set(key, value);
            //设置过期
            expire(key, expire);
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }
    }

    /**
     * 设置key 过期
     *
     * @param key
     * @param expire
     */
    public void expire(String key, Integer expire) {

        if (StringUtils.isBlank(key)) {
            return;
        }

        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            redis.expire(key, expire);
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }
    }

    /**
     * 发布消息
     *
     * @param key
     * @param value
     * @param waitSeconds
     * @param check
     * @param <T>
     * @return
     */
    public <T> Long rPush(String key, T value, int waitSeconds,Boolean check) {

        if(check){
            key = preKey + key;
        }

        Jedis redis = null;
        Long ret = null;
        try {

            redis = jedisPool.getResource();

            byte[] bytes = SerializeUtils.serialize(value);
            ret = redis.rpush(key.getBytes(), bytes);

            if (waitSeconds > 0) {
                redis.expire(key, waitSeconds);
            }
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }

        return ret;
    }

    /**
     * 订阅
     *
     * @param key
     * @param waitSeconds
     * @return
     */
    public Object blPop(String key, int waitSeconds,Boolean check) {

        Jedis redis = null;

        if(check){
            key = preKey + key;
        }

        try {

            redis = jedisPool.getResource();
            List<byte[]> values = redis.brpop(waitSeconds, key.getBytes());

            if (CollectionUtils.isNotEmpty(values)) {
                byte[] value = values.get(1);
                return SerializeUtils.deserialize(value);
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
            return null;
        } finally {
            close(redis);
        }
    }

    /**
     * 是否存在 key
     *
     * @param key
     * @param check
     * @return
     */
    public Boolean exit(String key,Boolean check) {

        if (StringUtils.isBlank(key)) {
            return Boolean.FALSE;
        }

        if(check){
            key = preKey + key;
        }

        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            return redis.exists(key);
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }

        return Boolean.FALSE;
    }

    /**
     * 移除 key
     *
     * @param key
     * @return
     */
    public long remove(String key,Boolean check) {

        if (StringUtils.isBlank(key)) {
            return 0L;
        }

        if(check){
            key = preKey + key;
        }

        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            return redis.del(key);
        } catch (Exception e) {
            log.error("Redis异常 key={},原因 msg={}", key, e.getMessage());
        } finally {
            close(redis);
        }

        return 0L;
    }

    /**
     * 批量移除
     * @param keys
     * @param check
     * @return
     */
    public long removeKeys(String keys,Boolean check){

        if (StringUtils.isBlank(keys)) {
            return 0L;
        }

        if(check){
            keys = preKey + keys;
        }

        long num = 0L;
        Jedis redis = null;
        try {

            redis = jedisPool.getResource();
            Set<String> list = redis.keys(keys+"*");
            if(CollectionUtils.isEmpty(list)){
                return 0L;
            }

            for (String key : list){
                num = num + redis.del(key);
            }
        } catch (Exception e) {
            log.error("批量移除Redis异常 keys={},原因 msg={}", keys, e.getMessage());
        } finally {
            close(redis);
        }

        return 0L;
    }

    /**
     * 关闭连接池
     *
     * @param redis
     */
    public void close(Jedis redis) {

        if (redis != null) {
            redis.close();
        }
    }

}
