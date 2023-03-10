package com.example.demo.servcie;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class CustomerImplTest extends ServiceImpl<CustomerMapper, UserInfo> implements CustomerServcie{
    @Override
    public Integer show(String aa) {
        System.out.println("aa");
        return null;
    }
}
