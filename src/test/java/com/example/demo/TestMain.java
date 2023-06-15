package com.example.demo;

import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class TestMain {

    static SqlSessionFactory sqlSessionFactory;
    //初始化
    static{
        String resource = "mybatis-config.xml";
        //获取资源
        try {
            InputStream is = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
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
        UserInfo userInfo = mapper.selectByUser(81);
        System.out.println(userInfo);
    }
}
