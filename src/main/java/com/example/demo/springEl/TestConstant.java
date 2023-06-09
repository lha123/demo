package com.example.demo.springEl;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class TestConstant {
    public static final String STR = "测试SpEL";
    public String nickname = "一线大码";
    public String name = "笑傲江湖";
    public int num = 5;
    public List<String> testList = Arrays.asList("aaa", "bbb", "ccc");
    public Map testMap = new HashMap() {{
        put("aaa", "元宇宙算法");
        put("hello", "world");
    }};
    public List cityList = new ArrayList<City>() {{
        add(new City("aaa", 500));
        add(new City("bbb", 600));
        add(new City("ccc", 1000));
        add(new City("ddd", 1000));
        add(new City("eee", 2000));
        add(new City("fff", 3000));
    }};

    public String showProperty() {
        return "Hello";
    }

    public String showProperty(String str) {
        return "Hello " + str + "!";
    }
}
