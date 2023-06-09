package com.example.demo.springEl;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangbo
 * @date 2021/11/26
 */
@Component
class Test1 implements SmartInitializingSingleton {

    @Autowired
    private TestSpringEL testSpringEL;


    @Override
    public void afterSingletonsInstantiated() {
        System.out.println(testSpringEL);
    }
}

