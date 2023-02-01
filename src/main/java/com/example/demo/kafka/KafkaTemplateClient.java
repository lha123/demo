package com.example.demo.kafka;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/client")
public class KafkaTemplateClient {

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//
//    @RequestMapping(value = "/send")
//    public void send() throws ExecutionException, InterruptedException {
//        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send("topic1","zd哈哈777");
//
//        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("ex"+ex);
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                System.out.println("dsf"+result.getProducerRecord());
//            }
//        });
//    }


//    @KafkaListener(topics = {"topic1"},groupId = "group1")
//    public void onMessage1(ConsumerRecord<String,String> consumerRecord, Acknowledgment ack){
//        System.out.println("onMessage1"+consumerRecord.value());
//        System.out.println();
//        ack.acknowledge();
//    }






}
