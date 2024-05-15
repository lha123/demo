package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CustomerImplTest extends ServiceImpl<CustomerMapper, UserInfo> implements CustomerServcie{

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    @Lazy
    private UserInfoService userInfoService;

    @Override
    public Integer show(String aa) {
        this.lambdaUpdate()
                .set(UserInfo::getAge,null)
                .eq(UserInfo::getId,81).update();
        return null;
    }
}
