package com.example.demo.main;

import cn.hutool.json.JSONUtil;
import com.example.demo.design23.UserService;
import com.example.demo.po.UserInfo;
import com.example.demo.service.CustomerServcie;
import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class TestMainEx extends TestMain {
    //需要测试的Service mapper 成员变量 加入Set 集合即可会更快
    private Set<Class<?>> filterClass = Sets.newHashSet(
            UserService.class
    );
    private CustomerServcie iService;

    @SneakyThrows
    @Test
    public void testUserExt() {
        UserInfo userInfo = iService.getById(258);
        System.out.println(JSONUtil.toJsonPrettyStr(userInfo));
    }

    @SneakyThrows
    @Test
    public void updateById() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        iService.updateById(userInfo);
        System.out.println(JSONUtil.toJsonPrettyStr(userInfo));
    }


    @Before
    public void show0() {
        iService = (CustomerServcie) super.show0(CustomerServcie.class);
    }

    @Override
    protected Set<Class<?>> getFilterClass() {
        return filterClass;
    }
}

