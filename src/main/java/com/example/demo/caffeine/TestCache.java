package com.example.demo.caffeine;

import cn.hutool.core.thread.ThreadUtil;
import com.github.benmanes.caffeine.cache.*;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.TimeUnit;

public class TestCache {

    TransactionTemplate template;

    @SneakyThrows
    public  void main(String[] args) {
        TransactionStatus a = null;
        Boolean execute = template.execute(e -> {
            template.execute(b->{

                e.setRollbackOnly();
                return true;
            });
            return true;
        });
        Cache<String, String> cache = Caffeine.newBuilder()
                .initialCapacity(100)//初始大小
                .maximumSize(200)//最大数量
                .expireAfterWrite(3, TimeUnit.SECONDS)//过期时间
                .scheduler(Scheduler.systemScheduler())
                .evictionListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(@Nullable String o, @Nullable String o2, RemovalCause removalCause) {
                        System.out.println("sdf"+o);
                        System.out.println("sdf"+o2);
                    }
                })
                .build();
            cache.put("as","");
            cache.put("aa","");
            cache.invalidate("aa");
//        TimeUnit.SECONDS.sleep(10);
//        Object as = cache.get("as", e -> "ee");
//        Object sfgsfg = cache.getIfPresent("as");
//        System.out.println(as);
//        System.out.println(sfgsfg);
        System.in.read();


    }
}
