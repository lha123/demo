package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.po.UserInfo;


public interface CustomerServcie extends IService<UserInfo> {

    Integer show(String aa);

}
