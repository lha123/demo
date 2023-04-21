package com.example.demo.Aop;

import com.example.demo.po.TestAa;
import org.springframework.stereotype.Service;

@Service
public class Dog implements DogApi{


    @Override
    public String show(TestAa a) {
        return "123";
    }

    @Override
    public Integer show1(String a) {
        return 321;
    }
}
