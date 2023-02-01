package com.example.demo;


import com.example.demo.conf.SwitchAspect;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfo;
import com.example.demo.po.UserInfoRolesVo;
import com.example.demo.po.UserInfoTest;
import com.example.demo.utils.QueryWrapJoinUtil;
import com.example.demo.utils.QueryWrapUtil;
import org.assertj.core.util.Lists;
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
    private SwitchAspect switchAspect;


    @Test
    public void testJoin(){
        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("a");
        //userInfo.setRoleId(234L);
        //userInfo.setAge(12);
//        userInfo.setEmail("l12773141269@163.com");
        //userInfo.setNames(Lists.newArrayList("a", "b"));
        List<UserInfoRolesVo> userInfoRolesVos = QueryWrapJoinUtil.selectJoinList(userInfo, UserInfoRolesVo.class, userInfoMapper);
        System.out.println(userInfoRolesVos);

    }

    @Test
    public void test(){
        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("a");
        //userInfo.setRoleId(234L);
        //userInfo.setAge(12);
//        userInfo.setEmail("l12773141269@163.com");
        //userInfo.setNames(Lists.newArrayList("a", "b"));
        List<UserInfo> userInfos = QueryWrapUtil.selectList(userInfo, customerMapper);
        System.out.println(userInfos);

    }









}
