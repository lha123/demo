package com.example.demo.Aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 接口实例工厂，这里主要是用于提供接口的实例对象
 */
//@Component //做实验用的
public class BizFactoryDog implements FactoryBean<DogRest>, BeanFactoryAware {

    private AbstractBeanFactory beanFactory;

    @Override
    public DogRest getObject() throws Exception {
        // 这里主要是创建接口对应的实例，便于注入到spring容器中
        InvocationHandler handler = new BizProxy<>(getObjectType(), beanFactory);
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

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (AbstractBeanFactory)beanFactory;
    }
}
