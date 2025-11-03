package com.example.demo;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.util.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.demo.annotation.*;
import com.example.demo.component.TestSingleton;
import com.example.demo.conf.ERRORAspect;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.MarketMapper;
import com.example.demo.mapper.ObjectMapper;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.po.UserInfo;
import com.example.demo.po.UserInfoRolesVo;
import com.example.demo.po.UserInfoTest;
import com.example.demo.service.CustomerFactory;
import com.example.demo.service.CustomerServcie;
import com.example.demo.utils.QueryWrapJoinUtil;
import com.example.demo.utils.QueryWrapUtil;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class TestUser {

    @Autowired
    private  UserInfoMapper userInfoMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerServcie customerServcie;

    @Autowired
    private ERRORAspect ERRORAspect;
    @Autowired
    private MarketMapper marketMapper;
    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestSingleton testSingleton;


    @Test
    public void testLog(){
        log.error("\u001B[31m"+"水电费第三方");
        System.out.println("\u001B[31m"+"水电费第三方");
    }






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
//        System.out.println(Thread.currentThread().getName());
//        testSingleton.show();

        UserInfo userInfo = customerMapper.selectByUser(81);
        System.out.println(userInfo);

    }

    @Test
    public void testJoin2(){
        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setName("aa");
        List<UserInfoRolesVo> userInfoRolesVos = customerServcie.selectJoinList(UserInfoRolesVo.class, getJoinPredicate(userInfo, UserInfo.class, UserInfoRolesVo.class));
        System.out.println("aa");
    }

    public static  <T> List<Field> getFields(Class<T>... paramClass){
        List<Field> fieldList = Lists.newArrayList();
        if(paramClass != null && paramClass.length > 0){
            List<Field> fields = Arrays.stream(paramClass)
                    .map(e->e.getDeclaredFields())
                    .flatMap(Arrays::stream).collect(Collectors.toList());
            fieldList.addAll(fields);
        }
        return fieldList;
    }


    private static <T,R> MPJLambdaWrapper<R> getJoinPredicate(T paramClass,Class<R> sourceClass,Class<?> returnClass) {
        MPJLambdaWrapper<R> wrapper = MPJWrappers.lambdaJoin();
        String targetName = StrUtil.lowerFirst(sourceClass.getSimpleName());
        wrapper.setAlias(targetName);
        YkcEntityMapping annotations = AnnotationUtil.getAnnotation(UserInfoTest.class, YkcEntityMapping.class);
        if(annotations != null){
            for (JoinMapping entityMapping : annotations.join()) {
                String joinClassName = !StrUtil.isBlank(entityMapping.joinAlias())?entityMapping.joinAlias():StrUtil.lowerFirst(entityMapping.joinClass().getSimpleName());
                String thisClassName = !StrUtil.isBlank(entityMapping.thisAlias())?entityMapping.thisField():targetName;
                if(!ArrayUtil.isEmpty(entityMapping.select())){
                    String[] selects = StreamUtil.of(entityMapping.select()).map(e -> joinClassName + "." + e)
                            .collect(Collectors.toList()).toArray(new String[]{});
                    wrapper.select(selects);
                }
                StringJoiner joiner = new StringJoiner(" ");
                String table = AnnotationUtil.getAnnotation(entityMapping.joinClass(), TableName.class).value();
                joiner.add(table);
                joiner.add(joinClassName);
                joiner.add("on");
                joiner.add(joinClassName+"."+StrUtil.toUnderlineCase(entityMapping.thisField()));
                joiner.add("=");
                joiner.add(thisClassName+"."+StrUtil.toUnderlineCase(entityMapping.joinField()));
                wrapper.join(entityMapping.joinType(),true,joiner.toString());
            }
        }

        List<Field> fieldList = getFields(paramClass.getClass());
        if(!CollUtil.isEmpty(fieldList)){
            //selectAll
            wrapper.selectAsClass(sourceClass,returnClass);

            for (Field field : fieldList) {
                field.setAccessible(true);
                Object fieldValue = ReflectUtil.getFieldValue(paramClass, field.getName());
                if("".equals(fieldValue) || ObjectUtil.isNull(fieldValue)){
                    continue;
                }
                QueryMatching annotation = field.getAnnotation(QueryMatching.class);
                //join as select
//                if(fieldValue instanceof Class<?>){
//                    JoinInfo joinInfos = annotation.join();
//                    if(joinInfos != null){
//                        String[] select = joinInfos.select();
//                        if(select != null && select.length > 0){
//                            wrapper.select(select);
//                        }
//                        wrapper.join(joinInfos.joinType(),true, StrUtil.join(" ",joinInfos.on()));
//                    }
//                    continue;
//                }
                // where
                String alias = annotation.alias() == Void.class?targetName:
                        StrUtil.lowerFirst(annotation.alias().getSimpleName());
                String fieldName = StrUtil.format("{}{}", alias.concat("."),StrUtil.toUnderlineCase(ReflectUtil.getFieldName(field)));
                switch (annotation.type()){
                    case eq:
                        wrapper.eq(fieldName,fieldValue);
                        break;
                }
            }
        }
        // order group limit
        OrderGroup annotation = paramClass.getClass().getAnnotation(OrderGroup.class);
        if(annotation != null){
            String[] groups = annotation.groupBy();
            if(!ArrayUtil.isEmpty(groups)){
                for (String group : groups) {
                    wrapper.groupBy(!StrUtil.isBlank(group),StrUtil.toUnderlineCase(group));
                }
            }
            OrderByIsAsc[] orderBys = annotation.orderBys();
            if(!ArrayUtil.isEmpty(orderBys)){
                for (OrderByIsAsc orderBy : orderBys) {
                    wrapper.orderBy(orderBy != null,orderBy.isAsc(),StrUtil.toUnderlineCase(orderBy.column()));
                }
            }
        }
        wrapper.last(Optional.ofNullable(annotation).map(e->e.last()).orElseGet(()->"limit 2000"));
        return wrapper;
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
        System.out.println("sdf");
//        cn.hutool.json.JSONObject user_info = marketMapper.selectById("user_info", 81);
//        System.out.println("sdf");
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
