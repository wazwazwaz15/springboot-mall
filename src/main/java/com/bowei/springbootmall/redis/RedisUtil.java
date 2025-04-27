package com.bowei.springbootmall.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    /*指令:https://blog.techbridge.cc/2016/06/18/redis-introduction/*/
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setStr(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void getStr(String key) {
        redisTemplate.opsForValue().get(key);
    }

    public void setObject(String key, Object value) {
        redisTemplate.opsForValue().set(key, value,1, TimeUnit.DAYS);
    }

    public Object getObject(String key) {

        return redisTemplate.opsForValue().get(key);
    }
}
