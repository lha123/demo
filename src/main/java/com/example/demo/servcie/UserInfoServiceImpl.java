package com.example.demo.servcie;

import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfo;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends MPJBaseServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{

}
