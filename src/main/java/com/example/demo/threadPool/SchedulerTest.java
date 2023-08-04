package com.example.demo.threadPool;

import cn.hutool.extra.spring.SpringUtil;
import lombok.SneakyThrows;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;


@Component
public class SchedulerTest {

    private static ConcurrentHashMap<String, ScheduledFuture> futureMap = new ConcurrentHashMap();


    @SneakyThrows
    @PostConstruct
    public void show(){
        ThreadPoolTaskScheduler bean = SpringUtil.getBean(ThreadPoolTaskScheduler.class);
        ScheduledFuture future = bean.schedule(()->{
            System.out.println("aaa");
        }, new CronTrigger("0/5 * * * * ?"));

        futureMap.put("123",future);

        new Thread(()->{
            ScheduledFuture future1 = futureMap.get("123");
            future1.cancel(true);
            // 如果任务取消需要消耗点时间
            boolean cancelled = future1.isCancelled();
            while (!cancelled) {
                cancelled = future1.cancel(true);
                System.out.println("aaaaabbbb");
            }
        }).start();

    }
}
