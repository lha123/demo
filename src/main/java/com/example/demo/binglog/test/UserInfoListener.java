package com.example.demo.binglog.test;

import com.example.demo.binglog.Binlog;
import com.example.demo.binglog.BinlogListener;
import com.example.demo.po.UserInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Binlog(hostName = "${log.binlog.hosts[0].host}",database = "${log.binlog.hosts[0].name}", tableSchemaClass = UserInfo.class)
public class UserInfoListener implements BinlogListener<UserInfo> {


    @Override
    public void onUpdate(UserInfo from, UserInfo to) {
        System.out.println("onUpdate");
    }

    @Override
    public void onInsert(UserInfo data) {
        System.out.println("onInsert");
    }

    @Override
    public void onDelete(UserInfo data) {
        System.out.println("onDelete");
    }
}
