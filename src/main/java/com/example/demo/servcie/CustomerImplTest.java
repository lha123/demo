package com.example.demo.servcie;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.component.TestSingleton;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerImplTest extends ServiceImpl<CustomerMapper, UserInfo> implements CustomerServcie{

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Integer show(String aa) {
        System.out.println("aa");
        System.out.println(customerMapper);
        List<UserInfo> userInfos = customerMapper.selectList(null);
        return null;
    }
}
