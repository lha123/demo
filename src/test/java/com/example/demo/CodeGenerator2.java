package com.example.demo;

import cn.hutool.core.util.StrUtil;
import com.example.demo.pojo.FromInfo;
import com.example.demo.pojo.ServiceInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeGenerator2  extends BaseCode{

    public static void main(String[] args) {

        List<FromInfo> aa = new ArrayList<>();
        aa.add(new FromInfo("客户名称","String","name"));
        executeFrom("From.java.vm","AaFrom",aa);
        executeFrom("Vo.java.vm","AaVo",aa);
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        ServiceInfo build = ServiceInfo.builder()
                .mappingName("show")
                .title("查询用户信息")
                .fromUpperCase("AaFrom")
                .fromLowerCase(StrUtil.lowerFirst("AaFrom"))
                .vo("AaVo").method("show").build();
        serviceInfos.add(build);
        executeService("Service.java.vm","AaService",serviceInfos);


    }


    public static void executeFrom(String templatePath, String className, List<FromInfo> list){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/com/example/demo";
        File file = new File(projectPath+"/aa/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package","com.example.demo.aa");
        map.put("className",className);
        map.put("fields",list);
        outputFile(file,map, "template/"+templatePath,true);
    }

    public static void executeService(String templatePath, String className, List<ServiceInfo> list){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/com/example/demo";
        File file = new File(projectPath+"/aa/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package","com.example.demo.aa");
        map.put("className",className);
        map.put("services",list);
        outputFile(file,map, "template/"+templatePath,true);
    }






}
