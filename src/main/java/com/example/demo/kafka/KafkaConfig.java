package com.example.demo.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConfig {

//    @Bean
//    public KafkaListenerContainerFactory<?> batchFactory(ConsumerFactory consumerFactory){
//        ConcurrentKafkaListenerContainerFactory<Integer,String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setConcurrency(1);
//        factory.getContainerProperties().setPollTimeout(1500);
//        factory.setBatchListener(true);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);//设置手动提交ackMode
//        return factory;
//    }


}
