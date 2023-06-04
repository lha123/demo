package com.example.demo;


import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.demo.servcie.CustomerServcie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@MapperScan("com.example.demo.mapper")
@MybatisPlusTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {CustomerServcie.class} ))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJunit {

    @Autowired
    private CustomerServcie servcie;


    @Test
    public void test1(){
        servcie.show("sdf");
    }
}
