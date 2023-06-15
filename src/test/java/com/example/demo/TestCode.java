package com.example.demo;

import cn.hutool.core.util.TypeUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.demo.po.UserInfo;
import lombok.SneakyThrows;
import org.apache.ibatis.reflection.TypeParameterResolver;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;


public class TestCode {

    private List<UserInfo> list;

    public List<UserInfo> getList() {
        return list;
    }

    @SneakyThrows
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(9194243645645645646L);
        userInfo.setName("sdf");
        userInfo.setAge(12);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(342342L);
        userInfo1.setName("dasdf");
        userInfo1.setAge(122);
//        String orderDetailString = JSON.toJSONString(userInfo, SerializerFeature.BrowserCompatible);
//        System.out.println(orderDetailString);
//        BigDecimal a = new BigDecimal("2");
//        a = a.add(new BigDecimal("1"));
//        System.out.println(a);

        List<UserInfo> list = new ArrayList<>(){};
        list.add(userInfo);
        list.add(userInfo1);
        Method method  = TestCode.class.getMethod("getList",null);
        Type returnType = TypeParameterResolver.resolveReturnType(method, TestCode.class);
        System.out.println(typeToClass(returnType));
        System.out.println(TypeUtil.getClass(TypeUtil.getTypeArgument(List.class.getGenericSuperclass())));
//        System.out.println(TypeUtil.getTypeArgument(list.getClass().getGenericSuperclass()));
//        List<UserInfo> list1 = (List<UserInfo>) JSONUtil.toList(JSONUtil.toJsonStr(list), TypeUtil.getClass(TypeUtil.getTypeArgument(list.getClass().getGenericSuperclass())));
//        System.out.println(JSONUtil.toJsonStr(list));
//        UserInfo userInfo3 = new UserInfo();
//        System.out.println(JSONUtil.toJsonPrettyStr(userInfo3));



        List<UserInfo> list11 = JSON.parseObject(JSONUtil.toJsonStr(list), new TypeReference<List>(){});
        System.out.println("sdf");











    }
    private static Class<?> typeToClass(Type src) {
        Class<?> result = null;
        if (src instanceof Class) {
            result = (Class<?>) src;
        } else if (src instanceof ParameterizedType) {
            result = (Class<?>) ((ParameterizedType) src).getRawType();
        } else if (src instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) src).getGenericComponentType();
            if (componentType instanceof Class) {
                result = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance(componentClass, 0).getClass();
            }
        }
        if (result == null) {
            result = Object.class;
        }
        return result;
    }




}
