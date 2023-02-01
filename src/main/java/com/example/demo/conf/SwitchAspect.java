package com.example.demo.conf;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SwitchAspect {

    @Pointcut("@annotation(com.example.demo.annotation.Switch)")
    public void operationLog() {}


    /**
     * 前置处理
     */
    @Before(value = "operationLog()")
    public void doBefore(JoinPoint joinPoint) {

        System.out.println("aaaa");
    }


}
