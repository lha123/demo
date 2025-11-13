package com.example.demo;



import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.example.demo.annotation.YkcMybatisRepo;
import com.example.demo.annotation.YkcMybatisRepoImpl;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Types;

public class CodeGenerator {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/demo", "root", "123456");

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
                            .moduleName("aa");

                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user") // 设置需要生成的表名
                            .addTablePrefix("t_")
                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class)
                            .entityBuilder()
                            .enableFileOverride()
                            .enableLombok() // 启用 Lombok
                            .enableChainModel() // 启用链式模型（@Accessors(chain = true)）
                            .repoBuilder()
                            .enableFileOverride()
                            .repoBuilder()
                            .enableFileOverride()
                            .superRepoClass(YkcMybatisRepo.class)
                            .superRepoImplClass(YkcMybatisRepoImpl.class)
                            .controllerBuilder()
                            .enableFileOverride()
                            .enableRestStyle(); // 启用 REST 风格
                })
                .templateConfig(builder -> {
                    // 如果使用了自定义模板，指定路径

                })
                .injectionConfig(builder->{
                    builder.customFile(new CustomFile.Builder()
                            .fileName("AddDTO.java")
                            .templatePath("/templates/addDTO.java.vm")
                            .packageName("dto").build());
                    builder.customFile(new CustomFile.Builder()
                            .fileName("UpdateDTO.java")
                            .templatePath("/templates/updateDTO.java.vm")
                            .packageName("dto").build());
                    builder.customFile(new CustomFile.Builder()
                            .fileName("DetailVO.java")
                            .templatePath("/templates/detailVO.java.vm")
                            .packageName("dto").build());

                    builder.customFile(new CustomFile.Builder()
                            .fileName("PageDTO.java")
                            .templatePath("/templates/pageDTO.java.vm")
                            .packageName("dto").build());

                    builder.customFile(new CustomFile.Builder()
                            .fileName("PageVO.java")
                            .templatePath("/templates/pageVO.java.vm")
                            .packageName("dto").build());

                })
                .execute();
    }


}


