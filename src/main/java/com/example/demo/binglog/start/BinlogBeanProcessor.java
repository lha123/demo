package com.example.demo.binglog.start;


import com.example.demo.binglog.BinlogListener;
import com.example.demo.binglog.LogBinlogConfig;
import com.example.demo.binglog.MysqlListener;
import com.example.demo.binglog.thread.BinlogThreadStarter;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class BinlogBeanProcessor implements SmartInitializingSingleton {
    private ApplicationContext context;
    @Autowired
    private Environment environment;
    @Autowired
    private LogBinlogConfig logBinlogConfig;

    public BinlogBeanProcessor(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, BinlogListener> beans = context.getBeansOfType(BinlogListener.class);

        Map<String, List<MysqlListener>> listeners = beans.values().stream()
                .map(l -> new MysqlListener(l, environment))
                .collect(Collectors.groupingBy(MysqlListener::getHostName));

        listeners.forEach((k, v) -> new BinlogThreadStarter().runThread(logBinlogConfig.getByNameAndThrow(k), v));
    }
}
