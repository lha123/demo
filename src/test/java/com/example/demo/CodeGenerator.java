package com.example.demo;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.google.common.collect.Lists;
import org.checkerframework.checker.units.qual.C;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://139.224.1.155:3306/todo", "comma-admin", "qloofwYNZGnttbse");

    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author("liuhonger") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir") + "/src/main/java/"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.example.demo") // 设置父包名
                            .moduleName("aa"); // 设置父包模块名

                })
                .strategyConfig(builder -> {
                    builder.addInclude("aa"); // 设置需要生成的表名
                }).execute();
    }



}


