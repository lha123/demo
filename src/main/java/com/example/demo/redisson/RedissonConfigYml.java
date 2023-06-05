package com.example.demo.redisson;


import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "person")
@PropertySource(value = "classpath:person.yml",factory = YamlPropertySourceFactory.class,encoding = "UTF-8")  //读取指定路径配置文件
public class RedissonConfigYml {

    private String id;

    private String name;

    private int age;

    private boolean isManager;

    private Date birthday;

    private Map<String, Object> map;

    private List<String> list;

    private Address address;


    @PostConstruct
    public void show(){
        System.out.println(this);
    }


}
