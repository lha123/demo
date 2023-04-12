package com.example.demo.Aop;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 接口实例工厂，这里主要是用于提供接口的实例对象
 */
@Component
public class BizFactoryS implements FactoryBean<DogInterFace> {



    @Override
    public DogInterFace getObject() throws Exception {
        // 这里主要是创建接口对应的实例，便于注入到spring容器中
        InvocationHandler handler = new BizProxy<>(null, null);
        return (DogInterFace) Proxy.newProxyInstance(null, new Class[]{null}, handler);
    }

    @Override
    public Class<DogInterFace> getObjectType() {
        return DogInterFace.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
