package com.example.demo;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.example.demo.annotation.YkcMybatisRepo;
import com.example.demo.annotation.YkcMybatisRepoImpl;
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
            .Builder("jdbc:mysql://81.69.195.111:23306/demo", "root", "bmV2eih*N3b2hlcmUK");

    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author("liuhonger") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(System.getProperty("user.dir") + "/src/main/java/")
                            .dateType(DateType.ONLY_DATE); // 设置日期类型为 Date
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
                    builder.addInclude("t_user") // 设置需要生成的表名
                            .addTablePrefix("t_")
                            .entityBuilder()
                            .enableFileOverride()
                            .enableLombok() // 启用 Lombok
                            .enableChainModel() // 启用链式模型（@Accessors(chain = true)）
                            .serviceBuilder()
                            .enableFileOverride()
                            .superServiceClass(YkcMybatisRepo.class)
                            .superServiceImplClass(YkcMybatisRepoImpl.class)
                            .formatServiceFileName("%sRepo")
                            .formatServiceImplFileName("%sRepoImp")
                            .controllerBuilder()
                            .enableFileOverride()
                            .enableRestStyle(); // 启用 REST 风格
                })
                .templateConfig(builder -> {
                    // 如果使用了自定义模板，指定路径
                    builder.serviceImpl("/template2/serviceImpl.java.vm");
                    builder.entity("/template2/entity.java.vm");
                })
                .execute();
    }



}


