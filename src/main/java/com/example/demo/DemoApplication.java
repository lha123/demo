package com.example.demo;


import cn.hutool.extra.spring.EnableSpringUtil;
import com.example.demo.annotation.EnableBizMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yeauty.annotation.EnableWebSocket;

@EnableSpringUtil
@SpringBootApplication
@EnableBizMapping(bizPackages = {"com.example.demo"})
//@EnableCaching
//@EnableFeignClients
@EnableWebSocket
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }



}
