package com.example.demo.servcie;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.annotation.Switch;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import com.example.demo.po.UserInfoTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerImpl extends ServiceImpl<CustomerMapper, UserInfo> implements CustomerServcie{

    private CustomerMapper customerMapper;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    @Transactional
    public Integer show(String aa) {
        applicationContext.publishEvent(new UserInfoTest());
        return null;
    }


}
