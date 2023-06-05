package com.example.demo;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.po.UserInfo;

import java.math.BigDecimal;


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

        System.out.println(StrUtil.format("dsfadf{}",343.45));



    }


}
