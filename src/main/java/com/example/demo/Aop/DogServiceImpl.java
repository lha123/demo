package com.example.demo.Aop;

import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.DogShow1;
import com.example.demo.po.TestAa;
import com.example.demo.po.UserInfo;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class DogServiceImpl implements DogServiceApi{

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AspectDemoService aspectDemoService;
    @Autowired
    private OrderService orderService;

    @Override
    public TestAa show(TestAa a) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(10);
        userInfo.setName("ttt");
        orderService.updateById(userInfo);
        return new TestAa();
    }

    @Override
    @Transactional
    public DogShow1 show1() {
        return new DogShow1();
    }
}
