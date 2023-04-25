package com.example.demo.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "mock")
@Component
public class MockProperties {

    private boolean isEnable;

    private String url;

}
