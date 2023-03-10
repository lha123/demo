package com.example.demo.utils;

import com.example.demo.po.UserInfo;
import com.google.common.collect.Lists;
import org.apache.catalina.User;
import org.apache.ibatis.jdbc.Null;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FastAutoGeneratorTest  {


    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        List<UserInfo> aa = Lists.newArrayList();
        UserInfo info = new UserInfo();
        info.setId(1L);
        info.setName("a");
        UserInfo info1 = new UserInfo();
        info1.setId(2L);
        info1.setName(null);
        aa.add(info);
        aa.add(info1);
        Map<Long, String> collect = aa.stream().collect(Collectors.toMap(UserInfo::getId, UserInfo::getName));
        System.out.println("a");
    }


    public static void  show(){


    }
}
