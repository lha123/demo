package com.example.demo;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class TestMain {

    static SqlSessionFactory sqlSessionFactory;
    //初始化
    static{
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
            MybatisConfiguration configuration = (MybatisConfiguration)xmlConfigBuilder.getConfiguration();

            configuration.addMapper(CustomerMapper.class);
            Resource[] resource2 = resolver.getResources("classpath:mapper/**/CustomerMapper.xml");


            sqlSessionFactoryBean.setConfiguration(configuration);
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(resource2);
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建工厂
    }

    public static SqlSession openSession(){
        //使用工厂 创建Sql会话（默认不提交）
        return sqlSessionFactory.openSession();
    }


    public static void main(String[] args) {
        CustomerMapper mapper = openSession().getMapper(CustomerMapper.class);
        UserInfo userInfo = mapper.selectByInfo(81);
        System.out.println(userInfo);
    }
}
