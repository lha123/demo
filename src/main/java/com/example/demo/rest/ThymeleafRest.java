package com.example.demo.rest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.po.pojo.ApiInfo;
import com.example.demo.po.pojo.FromInfo;
import com.example.demo.po.pojo.ServiceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
public class ThymeleafRest extends BaseCode{

    @GetMapping(value = {"/hello"})
    public ModelAndView index(ModelAndView model){
        return model;
    }
    @PostMapping(value = {"/addInfo"})
    public boolean index(@RequestBody ApiInfo apiInfo){
        executeOneApi(apiInfo);
        System.out.println(apiInfo);
        return true;
    }

    private static List<ServiceInfo> serviceInfoList = new ArrayList<>();
    public static void executeOneApi(ApiInfo info){
        executeFrom("Req.java.vm",info.getFromClass(),info.getFromList());
        executeFrom("Res.java.vm",info.getVoClass(),info.getVoList());
        ServiceInfo serviceInfo = new ServiceInfo(info.getTitle(),info.getMethod());
        serviceInfo.setRequestMode(info.getRequestMode());
        serviceInfo.setIsValid(Integer.valueOf(1).equals(info.getIsValid()));
        serviceInfo.setFromUpperCase(info.getFromClass());
        serviceInfo.setFromLowerCase(StrUtil.lowerFirst(info.getFromClass()));
        serviceInfo.setVo(info.getVoClass());
        serviceInfoList.add(serviceInfo);
        if(Integer.valueOf(1).equals(info.getCommitServiceMethod())){
            executeRest("Rest.java.vm",info.getRestName(), serviceInfoList,info.getServiceName());
            executeService("Service.java.vm",info.getServiceName(), serviceInfoList);
            serviceInfoList.clear();
        }
    }


    public static void executeFrom(String templatePath, String className, List<FromInfo> list){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/com/example/demo";
        File file = new File(projectPath+"/aa/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package","com.example.demo.aa");
        map.put("className",className);
        map.put("fields",list);

        List<String> typeList = list.stream().map(FromInfo::getFieldType)
                .collect(Collectors.toList());
        if(typeList.contains("Date")){
            map.put("Date",true);
        }
        if(typeList.contains("BigDecimal")){
            map.put("BigDecimal",true);
        }

        List<String> collect = list.stream().filter(e -> !StrUtil.isBlank(e.getVailMessage()))
                .map(FromInfo::getFieldType)
                .collect(Collectors.toList());
        if(!CollUtil.isEmpty(collect)){
            if(collect.contains("String")){
                map.put("NotBlank",true);
            }
            List<String> notCollect = collect.stream().filter(e -> !e.equals("String")).collect(Collectors.toList());
            if(!CollUtil.isEmpty(notCollect)){
                map.put("NotNull",true);
            }
        }
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

    public static void executeRest(String templatePath, String className, List<ServiceInfo> list,String serviceName){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/com/example/demo";
        File file = new File(projectPath+"/aa/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package","com.example.demo.aa");
        map.put("className",className);
        map.put("services",list);
        map.put("servicesName",serviceName);
        outputFile(file,map, "template/"+templatePath,true);
    }


}
