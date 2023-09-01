//package com.example.demo.redisson;
//
//import org.redisson.Redisson;
//import org.redisson.api.RBlockingQueue;
//import org.redisson.api.RDelayedQueue;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@Configuration
//public class RedissonConfig {
//
//    @Bean
//    public RedissonClient redissonClient() throws IOException {
//        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson.yml"));
//        return Redisson.create(config);
//    }
//
//    @Bean
//    public RBlockingQueue<String> blockingQueue(RedissonClient client){
//        return client.getBlockingQueue("TOKEN");
//    }
//
//    @Bean
//    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockingQueue,RedissonClient client){
//        return client.getDelayedQueue(blockingQueue);
//    }
//}
