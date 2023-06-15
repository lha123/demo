package com.example.demo;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.demo.servcie.CustomerServcie;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@MybatisPlusTest
@Commit
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestJunit {


    private CustomerServcie iService;


    private Map<Class<?>,IService> map = new HashMap<>();


    @SneakyThrows
    @Before
    public void show1(){
        Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(CustomerServcie.class));
        iService = (CustomerServcie)first.newInstance();
        map.put(first,iService);
        show2(first,iService);
    }



    @Test
    public void test1() {
        String first = CollUtil.getFirst(StrUtil.split("123,434", ","));
        System.out.println("df");
        iService.show("df");

    }










    @SneakyThrows
    public void show2(Class<?> fieldClass,IService iService){
        Field[] declaredFields = fieldClass.getDeclaredFields();
        Field baseMapper = fieldClass.getSuperclass().getDeclaredField("baseMapper");
        baseMapper.setAccessible(true);
        baseMapper.set(iService,SpringUtil.getBean((Class) TypeUtil.getTypeArgument(fieldClass,0)));
        for (Field declaredField : declaredFields) {
            if(BaseMapper.class.isAssignableFrom(declaredField.getType())){
                Object bean = SpringUtil.getBean(declaredField.getType());
                declaredField.setAccessible(true);
                declaredField.set(iService,bean);
            }
            if(IService.class.isAssignableFrom(declaredField.getType())){
                declaredField.setAccessible(true);
                Class<?> interfaceClass = declaredField.getType();
                Class first = CollUtil.getFirst(getAllInterfaceAchieveClass(interfaceClass));
                if(map.containsKey(first)){
                    IService service = map.get(first);
                    declaredField.set(iService, service);
                }else{
                    IService newInstance = (IService)first.newInstance();
                    map.put(first,newInstance);
                    declaredField.set(iService, newInstance);
                    show2(first,newInstance);
                }
            }
        }
    }




    @SuppressWarnings("unchecked")
    public static List<Class> getAllInterfaceAchieveClass(Class clazz) throws Throwable {
        ArrayList<Class> list = new ArrayList<>();
        // 判断是否是接口
        if (clazz.isInterface()) {
            ArrayList<Class> allClass = getAllClassByPath(clazz.getPackage().getName());
            /**
             * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
             */
            for (int i = 0; i < allClass.size(); i++) {
                // 排除抽象类
                if (Modifier.isAbstract(allClass.get(i).getModifiers())) {
                    continue;
                }
                // 判断是不是同一个接口
                if (clazz.isAssignableFrom(allClass.get(i))) {
                    if (!clazz.equals(allClass.get(i))) {
                        list.add(allClass.get(i));
                    }
                }
            }
        }
        return list;
    }



    public static ArrayList<Class> getAllClassByPath(String packagename) throws Throwable {
        ArrayList<Class> list = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagename.replace('.', '/');
        ArrayList<File> fileList = new ArrayList<>();
        Enumeration<URL> enumeration = classLoader.getResources(path);
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            fileList.add(new File(url.getFile()));
        }
        for (int i = 0; i < fileList.size(); i++) {
            list.addAll(findClass(fileList.get(i), packagename));
        }
        return list;
    }



    private static ArrayList<Class> findClass(File file, String packagename) throws ClassNotFoundException {
        ArrayList<Class> list = new ArrayList<>();
        if (!file.exists()) {
            return list;
        }
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                assert !file2.getName().contains(".");// 添加断言用于判断
                ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
                list.addAll(arrayList);
            } else if (file2.getName().endsWith(".class")) {
                // 保存的类文件不需要后缀.class
                list.add(Class.forName(packagename + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
            }
        }
        return list;
    }


}
