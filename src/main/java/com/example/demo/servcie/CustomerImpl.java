package com.example.demo.servcie;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class CustomerImpl extends ServiceImpl<CustomerMapper, UserInfo> implements CustomerServcie{

    private CustomerMapper customerMapper;


}
