package com.example.demo.Aop;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.AbstractBeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 接口实例工厂，这里主要是用于提供接口的实例对象
 */
public class BizFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceType;
    private AbstractBeanFactory beanFactory;

    public BizFactory(Class<T> interfaceType, AbstractBeanFactory beanFactory) {
        this.interfaceType = interfaceType;
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        // 这里主要是创建接口对应的实例，便于注入到spring容器中
        InvocationHandler handler = new BizProxy<>(interfaceType, beanFactory);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
