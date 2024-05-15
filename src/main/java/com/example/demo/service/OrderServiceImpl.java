package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<CustomerMapper, UserInfo> implements OrderService {
}
