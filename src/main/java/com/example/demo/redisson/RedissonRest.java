package com.example.demo.redisson;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo.po.UserInfo;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
public class RedissonRest implements InitializingBean {

    @Autowired
    private RBlockingQueue<String> blockingQueue;
    @Autowired
    private RDelayedQueue<String> delayedQueue;

    @Override
    public void afterPropertiesSet() throws Exception {
        Runnable runnable = ()->{
            try {
                while (true){
                    String take = blockingQueue.take();
                    System.out.println("blockingQueue===>"+take);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(runnable).start();
        System.out.println("take");
    }


    @GetMapping("/redissonRest")
    public void show(){
        delayedQueue.offer("aa",5, TimeUnit.SECONDS);
        System.out.println("send success");
    }

    @GetMapping("/redisTemplate")
    public void show2(Double max){ //升序分页 如果降序 就反过来就可以了
        RedisTemplate<String, UserInfo> redisTemplate = RedisTemplateTest.getRedisTemplate(UserInfo.class);
            Set<ZSetOperations.TypedTuple<UserInfo>> typedTuples = redisTemplate.opsForZSet()
                    .rangeByScoreWithScores("*:*门店顾问*", max == null?0:max,999999999, 0, 2);//每页两条数据
            System.out.println(JSONUtil.toJsonPrettyStr(typedTuples));
            if(!CollUtil.isEmpty(typedTuples)){
                System.out.println(CollUtil.getLast(typedTuples).getScore()+1);
            }


    }
}
