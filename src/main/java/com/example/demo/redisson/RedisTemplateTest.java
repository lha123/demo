package com.example.demo.redisson;

import cn.hutool.extra.spring.SpringUtil;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RedisTemplateTest implements InitializingBean {

    @Autowired
    private UserInfoMapper userInfoMapper;


    public static <T> RedisTemplate<String,T> getRedisTemplate(Class<T> o){
        return SpringUtil.getBean("redisTemplate",RedisTemplate.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {// 实现redis 按查询条件分页
        List<UserInfo> userInfos = userInfoMapper.selectList(null);
        RedisTemplate<String, UserInfo> redisTemplate = getRedisTemplate(UserInfo.class);
        Map<String, UserInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(e -> e.getId() + ":" + e.getName(), Function.identity()));
        redisTemplate.opsForHash().putAll("myhash",userInfoMap);

        String zSetKey = "*:*门店顾问*";
        HashOperations<String, String, UserInfo> hashOperations = redisTemplate.opsForHash();
        Cursor<Map.Entry<String, UserInfo>> myhash = hashOperations.scan("myhash", ScanOptions.scanOptions().match(zSetKey).build());
        List<UserInfo> collect = myhash.stream().map(Map.Entry::getValue).collect(Collectors.toList());
        Set<ZSetOperations.TypedTuple<UserInfo>> typedTuples = collect.stream().map(e -> ZSetOperations.TypedTuple.of(e, Double.valueOf(e.getId()))).collect(Collectors.toSet());
        redisTemplate.opsForZSet().add(zSetKey,typedTuples);
        //用来删除redis zset key用的 更新zset 数据
        RedisTemplate<String, String> setRedisTemplate = getRedisTemplate(String.class);
        setRedisTemplate.opsForSet().add("客户id",zSetKey);

    }
}
