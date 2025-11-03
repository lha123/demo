package com.example.demo.binglog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BinlogPropertyReader {

    @Autowired
    private Environment environment;

    public String getHost() {
        return environment.getProperty("log.binlog.hosts[0].host");
    }

    public String getDatabaseName() {
        return environment.getProperty("log.binlog.hosts[0].name");
    }
}
