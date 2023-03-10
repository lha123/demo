package com.example.demo.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface MarketRest {


    @GetMapping("/testRest")
    default Object test(){
        System.out.println("sdf");
        return "df";
    }
}
