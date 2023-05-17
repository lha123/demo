package com.example.demo.component;


import com.example.demo.annotation.Switch;
import com.example.demo.po.UserInfoTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestSingleton {


    @Autowired
    private ApplicationContext context;
    @Transactional
    public void show(){
        System.out.println(Thread.currentThread().getName());
        context.publishEvent(new UserInfoTest());
        //throw new RuntimeException("a");

    }
}
