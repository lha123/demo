package com.example.demo.Aop;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 接口实例工厂，这里主要是用于提供接口的实例对象
 */
@Component
public class BizFactoryDog extends ApplicationObjectSupport implements FactoryBean<DogRest> {



    @Override
    public DogRest getObject() throws Exception {
        // 这里主要是创建接口对应的实例，便于注入到spring容器中
        InvocationHandler handler = new BizProxy<>(getObjectType(), getApplicationContext());
        return (DogRest) Proxy.newProxyInstance(DogRest.class.getClassLoader(), new Class[]{DogRest.class}, handler);
    }

    @Override
    public Class<DogRest> getObjectType() {
        return DogRest.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
