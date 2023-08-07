package com.example.demo.Aop;


import com.example.demo.annotation.AspectDemoTest;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.po.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AspectDemoServiceImpl implements AspectDemoService{


    @Autowired
    private CustomerMapper customerMapper;

    @Override
    @Transactional
    @AspectDemoTest(value = "liu")
    public Integer show(String a) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(81L);
        userInfo.setName("aa");
        customerMapper.updateById(userInfo);
        return 1123;
    }
}
