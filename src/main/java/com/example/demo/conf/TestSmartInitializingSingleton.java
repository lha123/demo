package com.example.demo.conf;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

@Component
public class TestSmartInitializingSingleton extends ApplicationObjectSupport implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        ApplicationContext applicationContext = getApplicationContext();
        TestPathScanner testPathScanner = new TestPathScanner((BeanDefinitionRegistry)applicationContext,false);

        testPathScanner.doScan("com.example.demo.component");
    }


}
