package com.example.demo.main;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Sets;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 待完善
 */
public abstract class TestMain {


    protected abstract Set<Class<?>> getFilterClass();

    protected Map<Class<?>, Object> map = new HashMap<>();
    private Set<Class<?>> setClass = new HashSet<>();

    @SneakyThrows
    public Object show0(Class<?> cls) {
        Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(cls));
        IService iService = (IService) first.newInstance();
        map.put(first, iService);
        Set<Class<?>> list = new HashSet<>();
        show1(first, list); //获取所需的mapper 到 list 里面
        show2(list); //
        show3(first, iService);
        return iService;
    }


    static SqlSessionFactory sqlSessionFactory;

    //初始化
    public void show2(Set<Class<?>> list) {
        //获取资源
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://139.224.1.155:3306/teeth");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUsername("comma-admin");
            dataSource.setPassword("qloofwYNZGnttbse");

//            dataSource.setJdbcUrl("jdbc:mysql://rr-uf6e2z20yo4601re9ro.mysql.rds.aliyuncs.com:3306/teeth");
//            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            dataSource.setUsername("fs_read");
//            dataSource.setPassword("@foursmile425");

            MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource1 = resolver.getResource("classpath:mybatis-config.xml");
            MybatisXMLConfigBuilder xmlConfigBuilder = new MybatisXMLConfigBuilder(resource1.getInputStream(), null, null);
            MybatisConfiguration configuration = (MybatisConfiguration) xmlConfigBuilder.getConfiguration();
            List<Resource[]> resourceList = new ArrayList<>();
            for (Class<?> aClass : list) {
                configuration.addMapper(aClass);
                Resource[] resource2 = resolver.getResources("classpath:mapper/**/" + ClassUtil.getClassName(aClass, true) + ".xml");
                resourceList.add(resource2);
            }
            sqlSessionFactoryBean.setConfiguration(configuration);
            sqlSessionFactoryBean.setDataSource(dataSource);
            List<Resource> collect = resourceList.stream().flatMap(Arrays::stream).collect(Collectors.toList());
            sqlSessionFactoryBean.setMapperLocations(collect.toArray(new Resource[collect.size()]));
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建工厂
    }


    public static SqlSession openSession() {
        //使用工厂 创建Sql会话（默认不提交）
        return sqlSessionFactory.openSession();
    }

    /**
     * 获取需要ServiceImpl下的mapper 放到 list 里面
     *
     * @param fieldClass
     * @param list
     */
    @SneakyThrows
    public void show1(Class fieldClass, Set<Class<?>> list) {
        Field[] declaredFields = fieldClass.getDeclaredFields();
        Class aClass = (Class) TypeUtil.getTypeArgument(fieldClass, 0);
        if (aClass != null) {
            list.add(aClass);
        }
        for (Field declaredField : declaredFields) {
            if (!getFilterClass().contains(declaredField.getType())) {
                continue;
            }
            if (BaseMapper.class.isAssignableFrom(declaredField.getType())) {
                list.add(declaredField.getType());
            }else {
                if (IService.class.isAssignableFrom(declaredField.getType())) {
                    Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(declaredField.getType()));
                    if (first == null) {
                        continue;
                    }
                    if (!setClass.contains(first)) {
                        setClass.add(first);
                        show1(first, list);
                    }
                }
            }
        }
    }


    @SneakyThrows
    public void show3(Class<?> fieldClass, Object iService) {
        Field[] declaredFields = fieldClass.getDeclaredFields();
        Field baseMapper = Optional.ofNullable(fieldClass.getSuperclass()).map(e -> e.getSuperclass())
                .map(e -> {
                    try {
                        return e.getDeclaredField("baseMapper");
                    } catch (NoSuchFieldException noSuchFieldException) {
                        noSuchFieldException.printStackTrace();
                    }
                    return null;
                }).orElse(null);
        if (baseMapper != null) {
            baseMapper.setAccessible(true);
            try {
                Object mapper = openSession().getMapper((Class) TypeUtil.getTypeArgument(fieldClass, 0));
                baseMapper.set(iService, mapper);
            } catch (BindingException e) {
                System.out.println(e.getMessage());
            }
        }
        for (Field declaredField : declaredFields) {
            if (!getFilterClass().contains(declaredField.getType())) {
                continue;
            }
            if (BaseMapper.class.isAssignableFrom(declaredField.getType())) {
                Object bean = openSession().getMapper(declaredField.getType());
                declaredField.setAccessible(true);
                declaredField.set(iService, bean);
            }else {
                if (IService.class.isAssignableFrom(declaredField.getType())) {
                    declaredField.setAccessible(true);
                    Class<?> interfaceClass = declaredField.getType();
                    Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(interfaceClass));
                    if (first == null) {
                        continue;
                    }
                    if (map.containsKey(first)) {
                        Object service = map.get(first);
                        declaredField.set(iService, service);
                    } else {
                        Object newInstance = first.newInstance();
                        map.put(first, newInstance);
                        declaredField.set(iService, newInstance);
                        show3(first, newInstance);
                    }
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    public Set<Class<?>> getAllInterfaceAchieveClass(Class clazz) throws Throwable {
        if (clazz.isInterface()) {
            return ClassUtil.scanPackageBySuper(ClassUtil.getPackage(clazz), clazz);
        } else {
            return Sets.newHashSet(clazz);
        }
    }

}
