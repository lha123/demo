package com.example.demo.Aop;

import cn.hutool.aop.aspects.Aspect;

import java.lang.reflect.Method;

public class AnimalAspect implements Aspect {

    @Override
    public boolean before(Object o, Method method, Object[] objects) {
        return false;
    }

    @Override
    public boolean after(Object o, Method method, Object[] objects, Object o1) {
        return false;
    }

    @Override
    public boolean afterException(Object o, Method method, Object[] objects, Throwable throwable) {
        return false;
    }
}
