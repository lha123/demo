package com.example.demo;

import com.example.demo.po.UserInfo;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class TestCode {

    private List<UserInfo> userInfos;

    public List<UserInfo> getUserInfos() {
        return null;
    }

    private static final Map<String, ConcurrentHashMap<Integer,String>> onlineMap = new ConcurrentHashMap<>();

    @SneakyThrows
    public static void main(String[] args) {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(9194243645645645646L);
//        userInfo.setName("sdf");
//        userInfo.setAge(12);
//        UserInfo userInfo1 = new UserInfo();
//        userInfo1.setId(342342L);
//        userInfo1.setName("dasdf");
//        userInfo1.setAge(122);
//
//        List<UserInfo> list = new ArrayList<>();
//
//
//        System.out.println(String.format("%-" + 5 + "s", "userInfo"));
//        System.out.println();
        Set<String> set  = Sets.newConcurrentHashSet();
        System.out.println(set.add("a"));
        set.remove("a");
        System.out.println(set.add("a"));






    }







}
