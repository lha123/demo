package com.example.demo.api;


import cn.hutool.json.JSONUtil;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfoRolesVo;
import com.example.demo.po.UserInfoTest;
import com.example.demo.servcie.CustomerServcie;
import com.example.demo.utils.QueryWrapJoinUtil;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerRest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @GetMapping("/test")
    public Object test(){
        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("a");
        //userInfo.setRoleId(234L);
        //userInfo.setAge(12);
//        userInfo.setEmail("l12773141269@163.com");
        //userInfo.setNames(Lists.newArrayList("a", "b"));
        List<UserInfoRolesVo> userInfoRolesVos = QueryWrapJoinUtil.selectJoinList(userInfo, UserInfoRolesVo.class, userInfoMapper);
        return userInfoRolesVos;

    }
}
