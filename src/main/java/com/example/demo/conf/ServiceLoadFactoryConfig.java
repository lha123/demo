package com.example.demo.conf;


import com.example.demo.servcie.CustomerFactory;
import org.springframework.beans.factory.FactoryBean;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceLoadFactoryConfig {

    @Bean
    public FactoryBean getFactory(){
        ServiceLocatorFactoryBean serviceLoaderFactoryBean = new ServiceLocatorFactoryBean();
        serviceLoaderFactoryBean.setServiceLocatorInterface(CustomerFactory.class);
        return serviceLoaderFactoryBean;
    }


}
