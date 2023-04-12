package com.example.demo.Aop;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface DogInterFace {


    @GetMapping("/dog")
    default String show(){
        System.out.println("sdf");
        return "sdf";
    }


}
