package com.jg.project.cache.service;

import com.jg.project.cache.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** redis 服务
 * @ClassName RedisService
 * @Author huangGuoGang@qq.com
 * @Date 2019-07-10 10:54
 * @Version 1.0
 **/
@Component
public class RedisService {

    @Autowired
    private RedisClient redisClient;

    /**
     * 添加字符串对象
     * @param key
     * @param value
     * @param check
     */
    public void addString(String key,String value,Boolean check){

        redisClient.addString(key,value,check);
    }

    /**
     * 获取字符串对象
     * @param key
     * @param check
     * @return
     */
    public String getString(String key,Boolean check){

        return redisClient.getString(key,check);
    }

    /**
     * 批量移除对象
     * @param key
     * @return
     */
    public long removeKeys(String key,Boolean check){

        return redisClient.removeKeys(key,check);
    }
}
