package com.example.demo;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 待完善
 */
public abstract class TestMain {


    protected abstract Set<Class<?>> getFilterClass();

    protected Map<Class<?>, IService> map = new HashMap<>();
    private Set<Class<?>> setClass = new HashSet<>();

    @SneakyThrows
    public IService show0(Class<?> cls) {
        Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(cls));
        IService iService = (IService) first.newInstance();
        map.put(first, iService);
        Set<Class<?>> list = new HashSet<>();
        show1(first, list);
        show2(list);
        show3(first, iService);
        return iService;
    }


    static SqlSessionFactory sqlSessionFactory;

    //初始化
    public void show2(Set<Class<?>> list) {
        //获取资源
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://139.224.1.155:3306/todo");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUsername("comma-admin");
            dataSource.setPassword("qloofwYNZGnttbse");
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

    @SneakyThrows
    public void show1(Class fieldClass, Set<Class<?>> list) {
        Field[] declaredFields = fieldClass.getDeclaredFields();
        list.add((Class) TypeUtil.getTypeArgument(fieldClass, 0));
        for (Field declaredField : declaredFields) {
            if (!getFilterClass().contains(declaredField.getType())) {
                continue;
            }
            if (BaseMapper.class.isAssignableFrom(declaredField.getType())) {
                list.add(declaredField.getType());
            }
            if (IService.class.isAssignableFrom(declaredField.getType())) {
                Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(declaredField.getType()));
                if (first == null) {
                    continue;
                }
                if (!setClass.contains(first)) {
                    setClass.add(first);
                    list.add((Class) TypeUtil.getTypeArgument(first, 0));
                    show1(first, list);
                }
            }
        }
    }


    @SneakyThrows
    public void show3(Class<?> fieldClass, IService iService) {
        Field[] declaredFields = fieldClass.getDeclaredFields();
        Field baseMapper = fieldClass.getSuperclass().getSuperclass().getDeclaredField("baseMapper");
        baseMapper.setAccessible(true);
        try {
            Object mapper = openSession().getMapper((Class) TypeUtil.getTypeArgument(fieldClass, 0));
            baseMapper.set(iService, mapper);
        } catch (BindingException e) {
            System.out.println(e.getMessage());
        }
        for (Field declaredField : declaredFields) {
            if (!getFilterClass().contains(declaredField.getType())) {
                continue;
            }
            if (BaseMapper.class.isAssignableFrom(declaredField.getType())) {
                Object bean = openSession().getMapper(declaredField.getType());
                declaredField.setAccessible(true);
                declaredField.set(iService, bean);
            }
//            if (RedisService.class.isAssignableFrom(declaredField.getType())) {
//                declaredField.setAccessible(true);
//                declaredField.set(iService, redisService);
//            }
            if (IService.class.isAssignableFrom(declaredField.getType())) {
                declaredField.setAccessible(true);
                Class<?> interfaceClass = declaredField.getType();
                Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(interfaceClass));
                if (first == null) {
                    continue;
                }
                if (map.containsKey(first)) {
                    IService service = map.get(first);
                    declaredField.set(iService, service);
                } else {
                    IService newInstance = (IService) first.newInstance();
                    map.put(first, newInstance);
                    declaredField.set(iService, newInstance);
                    show3(first, newInstance);
                }
            }
        }
    }


    @SuppressWarnings("unchecked")
    public List<Class> getAllInterfaceAchieveClass(Class clazz) throws Throwable {
        ArrayList<Class> list = new ArrayList<>();
        // 判断是否是接口
        if (clazz.isInterface()) {
            ArrayList<Class> allClass = getAllClassByPath(clazz.getPackage().getName());
            /**
             * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
             */
            for (int i = 0; i < allClass.size(); i++) {
                // 排除抽象类
                if (Modifier.isAbstract(allClass.get(i).getModifiers())) {
                    continue;
                }
                // 判断是不是同一个接口
                if (clazz.isAssignableFrom(allClass.get(i))) {
                    if (!clazz.equals(allClass.get(i))) {
                        list.add(allClass.get(i));
                    }
                }
            }
        }
        return list;
    }


    public ArrayList<Class> getAllClassByPath(String packagename) throws Throwable {
        ArrayList<Class> list = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagename.replace('.', '/');
        ArrayList<File> fileList = new ArrayList<>();
        Enumeration<URL> enumeration = classLoader.getResources(path);
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            fileList.add(new File(url.getFile()));
        }
        for (int i = 0; i < fileList.size(); i++) {
            list.addAll(findClass(fileList.get(i), packagename));
        }
        return list;
    }


    private ArrayList<Class> findClass(File file, String packagename) throws ClassNotFoundException {
        ArrayList<Class> list = new ArrayList<>();
        if (!file.exists()) {
            return list;
        }
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                assert !file2.getName().contains(".");// 添加断言用于判断
                ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
                list.addAll(arrayList);
            } else if (file2.getName().endsWith(".class")) {
                // 保存的类文件不需要后缀.class
                list.add(Class.forName(packagename + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
            }
        }
        return list;
    }


    /**
     * 获取redis
     *
     * @return
     */
//    @SneakyThrows
//    private void getRedisService() {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName(properties.getHost());
//        configuration.setDatabase(properties.getDatabase());
//        configuration.setPort(properties.getPort());
//        configuration.setPassword(RedisPassword.of(properties.getPassword()));
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
//        factory.afterPropertiesSet();
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        redisService = new RedisServiceImpl();
//        Field field = redisService.getClass().getDeclaredField("stringRedisTemplate");
//        field.setAccessible(true);
//        field.set(redisService, template);
//    }
}

