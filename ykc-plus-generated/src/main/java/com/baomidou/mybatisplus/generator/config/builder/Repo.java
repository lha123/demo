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
package com.baomidou.mybatisplus.generator.config.builder;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.ITemplate;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import com.baomidou.mybatisplus.generator.util.ClassUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Repo属性配置
 *
 * @author nieqiurong 2020/10/11.
 * @since 3.5.0
 */
public class Repo implements ITemplate {

    private final static Logger LOGGER = LoggerFactory.getLogger(Repo.class);

    private Repo() {
    }

    /**
     * 自定义继承的Repo类全称，带包名
     */
    private String superRepoClass = ConstVal.SUPER_REPO_CLASS;

    /**
     * 自定义继承的RepoImpl类全称，带包名
     */
    private String superRepoImplClass = ConstVal.SUPER_REPO_IMPL_CLASS;

    @NotNull
    public String getSuperRepoClass() {
        return superRepoClass;
    }

    @NotNull
    public String getSuperRepoImplClass() {
        return superRepoImplClass;
    }

    /**
     * 转换输出Repo文件名称
     *
     * @since 3.5.0
     */
    private ConverterFileName converterRepoFileName = (entityName -> "" + entityName + ConstVal.REPO);

    /**
     * 转换输出RepoImpl文件名称
     *
     * @since 3.5.0
     */
    private ConverterFileName converterRepoImplFileName = (entityName -> entityName + ConstVal.REPO_IMPL);

    /**
     * 是否覆盖已有文件（默认 false）
     *
     * @since 3.5.2
     */
    private boolean fileOverride;

    @NotNull
    public ConverterFileName getConverterRepoFileName() {
        return converterRepoFileName;
    }

    @NotNull
    public ConverterFileName getConverterRepoImplFileName() {
        return converterRepoImplFileName;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    @Override
    @NotNull
    public Map<String, Object> renderData(@NotNull TableInfo tableInfo) {
        Map<String, Object> data = new HashMap<>();
        data.put("superRepoClassPackage", this.superRepoClass);
        data.put("superRepoClass", ClassUtils.getSimpleName(this.superRepoClass));
        data.put("superRepoImplClassPackage", this.superRepoImplClass);
        data.put("superRepoImplClass", ClassUtils.getSimpleName(this.superRepoImplClass));
        data.put("repoNameLowerCase", StrUtil.lowerFirst(tableInfo.getRepoName()));
        return data;
    }

    public static class Builder extends BaseBuilder {

        private final Repo repo = new Repo();

        public Builder(@NotNull StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * Repo接口父类
         *
         * @param clazz 类
         * @return this
         */
        public Builder superRepoClass(@NotNull Class<?> clazz) {
            return superRepoClass(clazz.getName());
        }

        /**
         * Repo接口父类
         *
         * @param superRepoClass 类名
         * @return this
         */
        public Builder superRepoClass(@NotNull String superRepoClass) {
            this.repo.superRepoClass = superRepoClass;
            return this;
        }

        /**
         * Repo实现类父类
         *
         * @param clazz 类
         * @return this
         */
        public Builder superRepoImplClass(@NotNull Class<?> clazz) {
            return superRepoImplClass(clazz.getName());
        }

        /**
         * Repo实现类父类
         *
         * @param superRepoImplClass 类名
         * @return this
         */
        public Builder superRepoImplClass(@NotNull String superRepoImplClass) {
            this.repo.superRepoImplClass = superRepoImplClass;
            return this;
        }

        /**
         * 转换输出Repo接口文件名称
         *
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertRepoFileName(@NotNull ConverterFileName converter) {
            this.repo.converterRepoFileName = converter;
            return this;
        }

        /**
         * 转换输出Repo实现类文件名称
         *
         * @param converter 　转换处理
         * @return this
         * @since 3.5.0
         */
        public Builder convertRepoImplFileName(@NotNull ConverterFileName converter) {
            this.repo.converterRepoImplFileName = converter;
            return this;
        }

        /**
         * 格式化Repo接口文件名称
         *
         * @param format 　格式
         * @return this
         * @since 3.5.0
         */
        public Builder formatRepoFileName(@NotNull String format) {
            return convertRepoFileName((entityName) -> String.format(format, entityName));
        }

        /**
         * 格式化Repo实现类文件名称
         *
         * @param format 　格式
         * @return this
         * @since 3.5.0
         */
        public Builder formatRepoImplFileName(@NotNull String format) {
            return convertRepoImplFileName((entityName) -> String.format(format, entityName));
        }

        /**
         * 覆盖已有文件（该方法后续会删除，替代方法为enableFileOverride方法）
         *
         * @see #enableFileOverride()
         */
        @Deprecated
        public Builder fileOverride() {
            LOGGER.warn("fileOverride方法后续会删除，替代方法为enableFileOverride方法");
            this.repo.fileOverride = true;
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public Builder enableFileOverride() {
            this.repo.fileOverride = true;
            return this;
        }

        @NotNull
        public Repo get() {
            return this.repo;
        }
    }
}
