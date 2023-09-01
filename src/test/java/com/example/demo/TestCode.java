package com.example.demo;

import com.example.demo.po.UserInfo;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class TestCode {

    private List<UserInfo> userInfos;

    public List<UserInfo> getUserInfos() {
        return null;
    }

    private static final Map<String, ConcurrentHashMap<Integer,String>> onlineMap = new ConcurrentHashMap<>();

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("水电费");
        final TestCode testCode = TestCode.class.getDeclaredConstructor().newInstance();
        System.out.println();
        TimeUnit.SECONDS.sleep(10);

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
        show();
    }


    public static void show(){
        System.out.println("dfdd");
        System.out.println("dfdd");
        System.out.println("dfdd");
        show1();
    }

    public static void show1(){
        System.out.println("aaaa");
    }







}
