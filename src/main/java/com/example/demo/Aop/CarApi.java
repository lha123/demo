package com.example.demo.Aop;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


public interface CarApi {

    @PostMapping("/showCar")
    String show1(List<String> a);
}
