package com.example.demo.binglog;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Binlog {
    String hostName();
    String database();
    Class tableSchemaClass();
}
