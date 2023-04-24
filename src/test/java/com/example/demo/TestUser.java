package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.conf.SwitchAspect;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.MarketMapper;
import com.example.demo.mapper.ObjectMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.*;
import com.example.demo.servcie.CustomerFactory;
import com.example.demo.servcie.CustomerServcie;
import com.example.demo.utils.QueryWrapJoinUtil;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TestUser {

    @Autowired
    private  UserInfoMapper userInfoMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerServcie customerImplTest;

    @Autowired
    private SwitchAspect switchAspect;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ApplicationContext context;






    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("df");
        list.add("df");
        list.add("df");
        list.add("df");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("a", list);
        System.out.println(jsonObj);

        List<Integer> list2 = jsonObj.getObject("a", new TypeReference<List<Integer>>(){});

        System.out.println(list2);

    }

    @Test
    public void ObjectyMapper() {
        // 初始化数据库脚本
        List<Object> objects = Lists.newArrayList();
        Object obj = new Object();
        obj.getClass().getName();



    }




    @Test
    public void testJoin(){
        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("a");
        List<UserInfoRolesVo> userInfoRolesVos = QueryWrapJoinUtil.selectJoinList(userInfo);
        System.out.println(userInfoRolesVos);

    }

    @Test
    public void test(){
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<UserInfo>()
                .set(UserInfo::getName,null)
                .eq(UserInfo::getId , 81);

        String sqlSet = wrapper.getSqlSet();
        System.out.println(sqlSet);
//        System.out.println("sdf");
//        JSONObject jsonObject = marketMapper.selectById("user_info", 81);
//        System.out.println("sdf");
//        customerServcie.show("sdf");

        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("a");
//        //userInfo.setRoleId(234L);
//        //userInfo.setAge(12);
////        userInfo.setEmail("l12773141269@163.com");
//        //userInfo.setNames(Lists.newArrayList("a", "b"));
//        List<UserInfo> userInfos = QueryWrapUtil.selectList(userInfo, customerMapper);
//        System.out.println(userInfos);

    }









}
