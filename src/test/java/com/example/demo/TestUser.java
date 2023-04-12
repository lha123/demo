package com.example.demo;




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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;

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


        System.out.println(ClassUtils.getPackageName(TestUser.class.getName()));

    }

    @Test
    public void ObjectyMapper(){
        Object bean = context.getBean(TestAa.class);
        System.out.println(bean);
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
