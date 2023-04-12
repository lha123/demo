package com.example.demo.Aop;

import cn.hutool.aop.ProxyUtil;
import org.springframework.stereotype.Component;


@Component
public class Cat {


    public String show(){
        return "a";
    }



}
