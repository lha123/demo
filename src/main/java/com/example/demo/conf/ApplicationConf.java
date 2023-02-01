package com.example.demo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class ApplicationConf {
    
    private String name;

    @PostConstruct
    public void init(){

        System.out.println("sdf");

    }
}
