package com.example.demo.rest;


import com.example.demo.po.UserInfoRolesVo;
import com.example.demo.po.UserInfoTest;
import com.example.demo.servcie.CustomerServcie;
import com.example.demo.utils.QueryWrapJoinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRest {


    @Autowired
    private CustomerServcie customerImplTest;

    @GetMapping("/test")
    public List<UserInfoRolesVo> test(Integer a){

        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("a");
        List<UserInfoRolesVo> userInfoRolesVos = QueryWrapJoinUtil.selectJoinList(userInfo);
        return userInfoRolesVos;
    }
}
