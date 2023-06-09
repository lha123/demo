package com.example.demo.redisson;


import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
