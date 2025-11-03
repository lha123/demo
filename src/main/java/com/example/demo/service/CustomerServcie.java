package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.UserInfo;
import com.github.yulichang.base.MPJBaseService;


public interface CustomerServcie extends MPJBaseService<UserInfo> {

    Integer show(String aa);

    default Integer show1(){
        return 1;
    }

}
