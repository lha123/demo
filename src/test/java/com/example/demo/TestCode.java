package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.po.UserInfo;
import com.example.demo.po.UserRoles;
import com.example.demo.pojo.TestFun;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.function.Supplier;


public class TestCode {

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(9194243645645645646L);
        userInfo.setName("sdf");
        userInfo.setAge(12);
        String orderDetailString = JSON.toJSONString(userInfo, SerializerFeature.BrowserCompatible);
        System.out.println(orderDetailString);
        BigDecimal a = new BigDecimal("2");
        a = a.add(new BigDecimal("1"));
        System.out.println(a);



    }


}
