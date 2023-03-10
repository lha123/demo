package com.example.demo;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;
import com.example.demo.conf.SwitchAspect;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.MarketMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfo;
import com.example.demo.po.UserInfoRolesVo;
import com.example.demo.po.UserInfoTest;
import com.example.demo.po.UserRoles;
import com.example.demo.servcie.CustomerFactory;
import com.example.demo.servcie.CustomerServcie;
import com.example.demo.utils.QueryWrapJoinUtil;
import com.example.demo.utils.QueryWrapUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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






    public static void main(String[] args) {



        System.out.println("dsf");

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
        List<UserInfo> userInfos = QueryWrapUtil.selectList(userInfo, customerMapper);
        System.out.println(userInfos);

    }









}
