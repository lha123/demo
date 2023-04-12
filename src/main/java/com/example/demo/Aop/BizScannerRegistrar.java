package com.example.demo.Aop;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.example.demo.annotation.BizImplements;
import com.example.demo.annotation.EnableBizMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BizScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanClassLoaderAware, BeanFactoryAware {

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(EnableBizMapping.class.getName()));
        if (attributes == null) {
            return;
        }

        Set<Class<?>> classes = new HashSet<>();
        for (String s : getPackagesToScan(attributes)) {
            classes.addAll(ClassUtil.scanPackageByAnnotation(s, BizImplements.class));
        }
        for (Class<?> beanClazz : classes) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            // 这里可以给该对象的属性注入对应的实例。mybatis就在这里注入了dataSource和sqlSessionFactory，
            // definition.getPropertyValues().add("interfaceType", beanClazz)，BeanClass需要提供setter
            // definition.getConstructorArgumentValues()，BeanClass需要提供包含该属性的构造方法，否则会注入失败
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanFactory);

            // 注意，这里的BeanClass是生成Bean实例的工厂，不是Bean本身。
            // FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，
            // 其返回的是该工厂Bean的getObject方法所返回的对象。
            definition.setBeanClass(BizFactory.class);

            //这里采用的是byType方式注入，类似的还有byName等
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);

            String simpleName = beanClazz.getSimpleName();
            log.info("beanClazz.getSimpleName(): {}", simpleName);
            log.info("GenericBeanDefinition: {}", definition);

            registry.registerBeanDefinition(beanClazz.getSimpleName()+"s", definition);
        }
    }


    /**
     * 获取扫描的基础包路径
     *
     * @return 基础包路径
     */
    private String[] getPackagesToScan(AnnotationAttributes attributes) {
        String[] basePackages = attributes.getStringArray("bizPackages");
        return basePackages;
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
