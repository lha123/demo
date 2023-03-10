package com.example.demo.conf;


import com.example.demo.po.UserInfoTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventListenerComponent {


    @EventListener
    public void processUserTest(UserInfoTest test){
        System.out.println(test);
        System.out.println(Thread.currentThread().getName());
    }

    @TransactionalEventListener
    public void processUserTesta(UserInfoTest test){
        System.out.println(test);
        System.out.println(Thread.currentThread().getName());
    }
}
