package com.example.demo.redisson;

import cn.hutool.extra.spring.SpringUtil;
import com.example.demo.po.UserInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateTest {

    public void show(){
        RedisTemplate<String, UserInfo> redisTemplate = getRedisTemplate(UserInfo.class);
        //redisTemplate.opsForList()
    }

    public static <T> RedisTemplate<String,T> getRedisTemplate(Class<T> o){
        return SpringUtil.getBean(RedisTemplate.class);
    }

}
