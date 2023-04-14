package com.example.demo.rest;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.component.TestSingleton;
import com.example.demo.mapper.ObjectMapper;
import com.example.demo.po.TestAa;
import com.example.demo.servcie.CustomerServcie;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class CustomerRest {

    @Autowired
    private CustomerServcie customerImplTest;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private TestSingleton testSingleton;

    @SneakyThrows
    @GetMapping("/test")
    public void test(Integer a) throws InterruptedException {
//        testSingleton.show();
//
//        customerImplTest.show("adsf");
//        String tt = "id int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
//        " title varchar(20) DEFAULT NULL,"+
//        " name varchar(20) DEFAULT NULL,"+
//        " age int(11) DEFAULT NULL";
//        objectMapper.createTable("aa",tt);
//        TestAa testAa = new TestAa();
//        testAa.setName("张工");
//        testAa.setTitle("eee");
//        testAa.setAge(12);
//        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(testAa);
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("(");
//        //Set<String> keys = this.sqlMap.keySet().stream().map(s -> "`"+s+"`").distinct().collect(Collectors.toSet());
//        stringBuilder.append(String.join(Constants.COMMA, stringObjectMap.keySet().stream().map(s -> "`"+s+"`").collect(Collectors.toList())));
//        stringBuilder.append(")");
//        stringBuilder.append("values");
//        stringBuilder.append("(");
//        //Set<String> values = this.sqlMap.values().stream().map(s -> "\""+s+"\"").distinct().collect(Collectors.toSet());
//        stringBuilder.append(String.join(Constants.COMMA, stringObjectMap.values().stream().map(e->StrUtil.toString(e)).map(e->"\'"+e+"\'").collect(Collectors.toList())));
//        stringBuilder.append(")");
//        objectMapper.insert("aa",stringBuilder.toString());
        System.out.println("sdf");
    }

    public static void main(String[] args) {
        StringJoiner joiner = new StringJoiner(",","(",")");
        joiner.add("aa");
        joiner.add("bb");
        System.out.println(joiner.toString());
    }

}
