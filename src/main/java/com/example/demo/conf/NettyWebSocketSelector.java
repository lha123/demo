package com.example.demo.conf;

import com.example.demo.Aop.CatInterfce;
import com.example.demo.Aop.DogHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

public class NettyWebSocketSelector {

    @Bean
    public DogHandler webSocketEndpointExporter() {
        return new DogHandler();

    }
}
