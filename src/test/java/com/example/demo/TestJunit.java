package com.example.demo;


import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.demo.servcie.CustomerServcie;
import com.example.demo.servcie.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisPlusTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {CustomerServcie.class, UserInfoService.class} ))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJunit {

    @Autowired
    private CustomerServcie servcie;


//    @Before
//    public void show1(){
//        System.out.println(servcie.getClass().getFields());
//        System.out.println("sdf");
//    }

    @Test
    public void test1() {
        servcie.show("sdf");
    }

}
