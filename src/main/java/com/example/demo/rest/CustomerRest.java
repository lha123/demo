package com.example.demo.rest;


import com.example.demo.component.TestSingleton;
import com.example.demo.mapper.ObjectMapper;
import com.example.demo.po.TestAa;
import com.example.demo.servcie.CustomerServcie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.StringJoiner;

@Slf4j
@RestController
@Api(value = "公告模块1", description = "公告模块1", tags = {"公告模块1"})
public class CustomerRest {

    @Autowired
    private CustomerServcie customerImplTest;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private TestSingleton testSingleton;

    private ApplicationContext context;

    @SneakyThrows
    @ApiOperation(value = "添加修改公告1",notes = "添加修改公告1")
    @GetMapping("/test")
    public TestAa test() throws InterruptedException {
        TestAa aa = new TestAa();
        aa.setCode(123);
        aa.setCode1(321);
        return aa;
//        customerImplTest.show("啊");
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
    }

    public static void main(String[] args) {
        StringJoiner joiner = new StringJoiner(",","(",")");
        joiner.add("aa");
        joiner.add("bb");
        System.out.println(joiner.toString());
    }

}
