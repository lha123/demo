/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 包相关的配置项
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
public class PackageConfig {

    private PackageConfig() {
    }

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "";

    /**
     * 父包模块名
     */
    private String moduleName = "";

    /**
     * Entity包名
     */
    private String entity = "entity";
    private String entityPackage = "entity";


    private String dto = "dto";
    private String dtoPackage = "dto";


    /**
     * Repo包名
     */
    private String repo = "repo";
    private String repoPackage = "repo";

    /**
     * Repo Impl包名
     */
    private String repoImpl = "repo.impl";
    private String repoImplPackage = "repo.impl";

    /**
     * Service包名
     */
    private String service = "service";
    private String servicePackage = "service";

    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    private String serviceImplPackage = "service.impl";

    /**
     * Mapper包名
     */
    private String mapper = "mapper";
    private String mapperPackage = "mapper";

    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";

    /**
     * Controller包名
     */
    private String controller = "controller";
    private String controllerPackage = "controller";

    /**
     * 路径配置信息
     */
    private Map<OutputFile, String> pathInfo;

    /**
     * 包配置信息
     *
     * @since 3.5.0
     */
    private final Map<String, String> packageInfo = new HashMap<>();

    /**
     * 父包名
     */
    @NotNull
    public String getParent() {
        if (StringUtils.isNotBlank(moduleName)) {
            return parent + StringPool.DOT + moduleName;
        }
        return parent;
    }

    /**
     * 连接父子包名
     *
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    @NotNull
    public String joinPackage(String subPackage) {
        String parent = getParent();
        return StringUtils.isBlank(parent) ? subPackage : (parent + StringPool.DOT + subPackage);
    }

    /**
     * 获取包配置信息
     *
     * @return 包配置信息
     * @since 3.5.0
     */
    @NotNull
    public Map<String, String> getPackageInfo() {
        if (packageInfo.isEmpty()) {
            packageInfo.put(ConstVal.MODULE_NAME, this.getModuleName());
            packageInfo.put(ConstVal.ENTITY, this.joinPackage(this.getEntity()));
            packageInfo.put(ConstVal.MAPPER, this.joinPackage(this.getMapper()));
            packageInfo.put(ConstVal.XML, this.joinPackage(this.getXml()));
            packageInfo.put(ConstVal.DTO, this.joinPackage(this.getDto()));
            packageInfo.put(ConstVal.REPO, this.joinPackage(this.getRepo()));
            packageInfo.put(ConstVal.REPO_IMPL, this.joinPackage(this.getRepoImpl()));
            packageInfo.put(ConstVal.SERVICE, this.joinPackage(this.getService()));
            packageInfo.put(ConstVal.SERVICE_IMPL, this.joinPackage(this.getServiceImpl()));
            packageInfo.put(ConstVal.CONTROLLER, this.joinPackage(this.getController()));
            packageInfo.put(ConstVal.PARENT, this.getParent());

            packageInfo.put(ConstVal.ENTITY_PACK, this.joinPackage(this.getEntityPackage()));
            packageInfo.put(ConstVal.MAPPER_PACK, this.joinPackage(this.getMapperPackage()));
            packageInfo.put(ConstVal.DTO_PACK, this.joinPackage(this.getDtoPackage()));
            packageInfo.put(ConstVal.REPO_PACK, this.joinPackage(this.getRepoPackage()));
            packageInfo.put(ConstVal.REPO_IMPL_PACK, this.joinPackage(this.getRepoImplPackage()));
            packageInfo.put(ConstVal.SERVICE_PACK, this.joinPackage(this.getServicePackage()));
            packageInfo.put(ConstVal.SERVICE_IMPL_PACK, this.joinPackage(this.getServiceImplPackage()));
            packageInfo.put(ConstVal.CONTROLLER_PACK, this.joinPackage(this.getControllerPackage()));
        }
        return Collections.unmodifiableMap(this.packageInfo);
    }

    /**
     * 获取包配置信息
     *
     * @param module 模块
     * @return 配置信息
     * @since 3.5.0
     */
    public String getPackageInfo(String module) {
        return getPackageInfo().get(module);
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getEntity() {
        return entity;
    }

    public String getService() {
        return service;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public String getRepo() {
        return repo;
    }

    public String getRepoImpl() {
        return repoImpl;
    }

    public String getMapper() {
        return mapper;
    }

    public String getXml() {
        return xml;
    }

    public String getDto() {
        return dto;
    }

    public String getController() {
        return controller;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public String getDtoPackage() {
        return dtoPackage;
    }

    public String getRepoPackage() {
        return repoPackage;
    }

    public String getRepoImplPackage() {
        return repoImplPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public Map<OutputFile, String> getPathInfo() {
        return pathInfo;
    }

    /**
     * 构建者
     *
     * @author nieqiurong
     * @since 3.5.0
     */
    public static class Builder implements IConfigBuilder<PackageConfig> {

        private final PackageConfig packageConfig;

        public Builder() {
            this.packageConfig = new PackageConfig();
        }

        public Builder(@NotNull String parent, @NotNull String moduleName) {
            this();
            this.packageConfig.parent = parent;
            this.packageConfig.moduleName = moduleName;
        }

        /**
         * 指定父包名
         *
         * @param parent 父包名
         * @return this
         */
        public Builder parent(@NotNull String parent) {
            this.packageConfig.parent = parent;
            return this;
        }

        /**
         * 指定模块名称
         *
         * @param moduleName 模块名
         * @return this
         */
        public Builder moduleName(@NotNull String moduleName) {
            this.packageConfig.moduleName = moduleName;
            return this;
        }

        /**
         * 指定实体包名
         *
         * @param entity 实体包名
         * @return this
         */
        public Builder entity(@NotNull String entity,String entityPackage) {
            this.packageConfig.entity = entity;
            this.packageConfig.entityPackage = entityPackage;
            return this;
        }

        public Builder dto(String dtoPackage) {
            this.packageConfig.dtoPackage = dtoPackage;
            return this;
        }


        /**
         * 指定service接口包名
         *
         * @param repo service包名
         * @return this
         */
        public Builder repo(@NotNull String repo,String repoPackage) {
            this.packageConfig.repo = repo;
            this.packageConfig.repoPackage = repoPackage;
            return this;
        }

        /**
         * service实现类包名
         *
         * @param repoImpl service实现类包名
         * @return this
         */
        public Builder repoImpl(@NotNull String repoImpl,String repoImplPackage) {
            this.packageConfig.repoImpl = repoImpl;
            this.packageConfig.repoImplPackage = repoImplPackage;
            return this;
        }

        /**
         * 指定service接口包名
         *
         * @param service service包名
         * @return this
         */
        public Builder service(@NotNull String service,String servicePackage) {
            this.packageConfig.service = service;
            this.packageConfig.servicePackage = servicePackage;
            return this;
        }

        /**
         * service实现类包名
         *
         * @param serviceImpl service实现类包名
         * @return this
         */
        public Builder serviceImpl(@NotNull String serviceImpl,String serviceImplPackage) {
            this.packageConfig.serviceImpl = serviceImpl;
            this.packageConfig.serviceImplPackage = serviceImplPackage;
            return this;
        }

        /**
         * 指定mapper接口包名
         *
         * @param mapper mapper包名
         * @return this
         */
        public Builder mapper(@NotNull String mapper, @NotNull String mapperPackage) {
            this.packageConfig.mapper = mapper;
            this.packageConfig.mapperPackage = mapperPackage;
            return this;
        }

        /**
         * 指定xml包名
         *
         * @param xml xml包名
         * @return this
         */
        public Builder xml(@NotNull String xml) {
            this.packageConfig.xml = xml;
            return this;
        }

        /**
         * 指定控制器包名
         *
         * @param controller 控制器包名
         * @return this
         */
        public Builder controller(@NotNull String controller,String controllerPackage) {
            this.packageConfig.controller = controller;
            this.packageConfig.controllerPackage = controllerPackage;
            return this;
        }

        /**
         * 路径配置信息
         *
         * @param pathInfo 路径配置信息
         * @return this
         */
        public Builder pathInfo(@NotNull Map<OutputFile, String> pathInfo) {
            this.packageConfig.pathInfo = pathInfo;
            return this;
        }

        /**
         * 连接父子包名
         *
         * @param subPackage 子包名
         * @return 连接后的包名
         */
        @NotNull
        public String joinPackage(@NotNull String subPackage) {
            return this.packageConfig.joinPackage(subPackage);
        }

        /**
         * 构建包配置对象
         * <p>当指定{@link #parent(String)} 与 {@link #moduleName(String)}时,其他模块名字会加上这两个作为前缀</p>
         * <p>
         * 例如:
         * <p>当设置 {@link #parent(String)},那么entity的配置为 {@link #getParent()}.{@link #getEntity()}</p>
         * <p>当设置 {@link #parent(String)}与{@link #moduleName(String)},那么entity的配置为 {@link #getParent()}.{@link #getModuleName()}.{@link #getEntity()} </p>
         * </p>
         *
         * @return 包配置对象
         */
        @Override
        public PackageConfig build() {
            return this.packageConfig;
        }
    }
}
