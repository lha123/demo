package com.im;

import com.example.demo.Aop.DogHandler;
import org.springframework.context.annotation.Bean;

public class NettyWebSocketSelector {

    @Bean
    public DogHandler webSocketEndpointExporter() {
        return new DogHandler();

    }
}
