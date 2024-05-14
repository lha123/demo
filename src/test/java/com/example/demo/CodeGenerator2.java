package com.example.demo;

import cn.hutool.core.util.StrUtil;
import com.example.demo.pojo.ApiInfo;
import com.example.demo.pojo.FromInfo;
import com.example.demo.pojo.ServiceInfo;
import org.assertj.core.util.Lists;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeGenerator2  extends BaseCode{

    public static void main(String[] args) {
        ApiInfo info = new ApiInfo();
        info.setFromClass("AaFrom");
        info.setVoClass("AaVo");
        info.setServiceClass("AaService");
        info.setFromList(Lists.newArrayList(new FromInfo("客户信息","Integer","name","客户不能为空!")));
        info.setVoList(Lists.newArrayList(new FromInfo("年龄","Integer","age")));
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        ServiceInfo serviceInfo = new ServiceInfo("获取信息1","show5551");
        serviceInfo.setRequestMode("POST");
        serviceInfo.setIsValid(true);
        serviceInfo.setFromUpperCase(info.getFromClass());
        serviceInfo.setFromLowerCase(StrUtil.lowerFirst(info.getFromClass()));
        serviceInfo.setVo(info.getVoClass());
        serviceInfos.add(serviceInfo);
        info.setServiceInfoList(serviceInfos);
        executeOneApi(info);

    }


    public static void executeOneApi(ApiInfo info){
        executeFrom("Req.java.vm",info.getFromClass(),info.getFromList());
        executeFrom("Res.java.vm",info.getVoClass(),info.getVoList());
        executeService("Service.java.vm",info.getServiceClass(),info.getServiceInfoList());
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
        outputFile(file,map, "template/"+templatePath,false);
    }






}
