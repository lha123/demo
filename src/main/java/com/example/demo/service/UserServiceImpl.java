package com.example.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.design23.UserService;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cn.hutool.core.util.StrUtil;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<CustomerMapper, UserInfo> implements UserService {
    
    
    @Override
    public void show() {
        UserInfo userInfo = this.getById(1);
        Assert.notNull(userInfo, "用户信息不存在");
    }

    private void show123(){
        System.out.println("aa");
    }

  
}
