package com.example.demo;

import com.example.demo.po.UserInfo;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;


public class TestCode {

    private List<UserInfo> userInfos;

    public List<UserInfo> getUserInfos() {
        return null;
    }

    @SneakyThrows
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(9194243645645645646L);
        userInfo.setName("sdf");
        userInfo.setAge(12);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(342342L);
        userInfo1.setName("dasdf");
        userInfo1.setAge(122);
//        String orderDetailString = JSON.toJSONString(userInfo, SerializerFeature.BrowserCompatible);
//        System.out.println(orderDetailString);
//        BigDecimal a = new BigDecimal("2");
//        a = a.add(new BigDecimal("1"));
//        System.out.println(a);
        List<UserInfo> list = new ArrayList<>();


        System.out.println(String.format("%-" + 5 + "s", "userInfo"));
        System.out.println();


    }







}
