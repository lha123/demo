package com.example.demo.springEl;

import com.example.demo.service.CustomerServcie;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonPo implements SmartInitializingSingleton {

    @Value("${driverClassName}")
    private String driver;
    @Autowired
    private CustomerServcie customerImplTest;

    public CommonPo() {
        System.out.println("sdf");
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("sdf");
    }
}
