package com.example.demo;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import org.junit.jupiter.api.Test;

import static com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder.globalConfig;
import static com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder.strategyConfig;

public class Code {
    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://xxxx:3306/baomidou?serverTimezone=Asia/Shanghai", "root", "123456")
            .schema("baomidou")
            .build();

    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig());
        generator.global(globalConfig());
        generator.execute();
    }
}
