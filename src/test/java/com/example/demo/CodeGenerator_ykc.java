package com.example.demo;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.example.demo.annotation.YkcMybatisRepo;
import com.example.demo.annotation.YkcMybatisRepoImpl;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Types;

public class CodeGenerator_ykc {

    @AllArgsConstructor
    enum PathEnum {

        OMP_STATION("omp-resource","com.omp.station","omp-station",
                new String[]{"omp-station-core","omp-station-infrastructure","omp-station-server","omp-station-service"},
                new String[]{
                         ".price.domain.entity"
                        ,".price.infrastructure.repository"
                        ,".price.infrastructure.repository.impl"
                        ,".price.infrastructure.repository.impl.mybatis"
                        ,".price.intf.web"
                        ,".price.service"
                        ,".price.service.impl"
                }),


        OMP_ORDER("omp-trading","com.omp.order","omp-order",
                new String[]{"omp-order-core","omp-order-infrastructure","omp-order-server","omp-order-service"},
                new String[]{
                        ".order.domain.entity"
                        ,".order.infrastructure.repository"
                        ,".order.infrastructure.repository.impl"
                        ,".order.infrastructure.repository.impl.mybatis"
                        ,".order.intf.web"
                        ,".order.service"
                        ,".order.service.impl"
                }),



        ;

        private String module;
        private String packagePath;
        private String pathModule;
        private String[] filePath;
        private String[] packPath;



    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://ttctest.mysql.polardb.rds.aliyuncs.com:3306/omp_resource", "superuser", "Evchong1qazxsw2");

    public static void main(String[] args) {
        PathEnum s =  PathEnum.OMP_STATION;
        String[] filePath = s.filePath;
        String[] packPath = s.packPath;


        String path = StrUtil.format("/Users/lha/charging-luoshu/charging-luoshu/{}/{}",s.module,s.pathModule);

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author("liuhonger") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(path)
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
                    builder // 设置文件 （路径——包名）
                            .controller(filePath[2]+".src.main.java.",s.packagePath+packPath[4])
                            .service(filePath[3]+".src.main.java.",s.packagePath+packPath[5])
                            .serviceImpl(filePath[3]+".src.main.java.",s.packagePath+packPath[6])

                            .entity(filePath[0]+".src.main.java."+s.packagePath+packPath[0],s.packagePath+packPath[0])
                            .repo(filePath[0]+".src.main.java."+s.packagePath+packPath[1],s.packagePath+packPath[1])
                            .repoImpl(filePath[1]+".src.main.java."+s.packagePath+packPath[2],s.packagePath+packPath[2])
                            .mapper(filePath[1]+".src.main.java."+s.packagePath+packPath[3],s.packagePath+packPath[3])
                            .xml(filePath[1]+".src.main.resources."+s.packagePath+packPath[3]);


                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_station_charge_vip_price_history") // 设置需要生成的表名
                            .addTablePrefix("t_station")
                            .mapperBuilder()
                            .enableFileOverride()
                            .mapperAnnotation(Mapper.class)
                            .entityBuilder()
                            .enableFileOverride()
                            .enableLombok() // 启用 Lombok
                            .enableChainModel() // 启用链式模型（@Accessors(chain = true)）
                            .repoBuilder()
                            .enableFileOverride()
                            .superRepoClass(YkcMybatisRepo.class)
                            .superRepoImplClass(YkcMybatisRepoImpl.class)

                            .serviceBuilder()
                            .enableNotCreate()
                            .enableFileOverride()
                            .controllerBuilder()
                            .enableNotCreate()
                            .enableFileOverride()
                            .enableRestStyle(); // 启用 REST 风格
                })
                .templateConfig(builder -> {
                    // 如果使用了自定义模板，指定路径

                })
//                .injectionConfig(builder->{
//                    builder.customFile(new CustomFile.Builder()
//                            .fileName("AddDTO.java")
//                            .templatePath("/templates/addDTO.java.vm")
//                            .packageName("dto").build());
//                    builder.customFile(new CustomFile.Builder()
//                            .fileName("UpdateDTO.java")
//                            .templatePath("/templates/updateDTO.java.vm")
//                            .packageName("dto").build());
//                    builder.customFile(new CustomFile.Builder()
//                            .fileName("DetailVO.java")
//                            .templatePath("/templates/detailVO.java.vm")
//                            .packageName("dto").build());
//
//                    builder.customFile(new CustomFile.Builder()
//                            .fileName("PageDTO.java")
//                            .templatePath("/templates/pageDTO.java.vm")
//                            .packageName("dto").build());
//
//                    builder.customFile(new CustomFile.Builder()
//                            .fileName("PageVO.java")
//                            .templatePath("/templates/pageVO.java.vm")
//                            .packageName("dto").build());
//
//                })
                .execute();
    }


}


