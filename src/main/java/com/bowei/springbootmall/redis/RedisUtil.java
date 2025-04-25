package com.bowei.springbootmall.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
/*指令:https://blog.techbridge.cc/2016/06/18/redis-introduction/*/
    @Autowired
    private RedisTemplate<String, String> redisTemplate ;

    public  void setStr(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void  getStr(String key){
        redisTemplate.opsForValue().get(key);
    }

}
