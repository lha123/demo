package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{

//    @Autowired
//    private OrderService orderService;
    @Autowired
    private CustomerServcie customerServcie;

    @Override
    public void show1() {
        System.out.println("UserInfoServiceImpl");
    }
}
