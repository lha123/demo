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
import java.util.Arrays;
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
        executeFrom("Req.java.vm",info.getFromClass(),info.getFromList(),info.getFromPackage());
        executeFrom("Res.java.vm",info.getVoClass(),info.getVoList(),info.getVoPackage());
        ServiceInfo serviceInfo = new ServiceInfo(info.getTitle(),info.getMethod());
        serviceInfo.setRequestMode(info.getRequestMode());
        serviceInfo.setIsValid(Integer.valueOf(1).equals(info.getIsValid()));
        serviceInfo.setFromUpperCase(info.getFromClass());
        serviceInfo.setFromLowerCase(StrUtil.lowerFirst(info.getFromClass()));
        serviceInfo.setVo(info.getVoClass());
        serviceInfo.setPackages(new String[]{info.getFromPackage().concat(".").concat(info.getFromClass()).concat(";"),
                info.getVoPackage().concat(".").concat(info.getVoClass()).concat(";")});
        serviceInfoList.add(serviceInfo);
        if(Integer.valueOf(1).equals(info.getCommitServiceMethod())){
            executeRest("Rest.java.vm",info.getRestName(), serviceInfoList,info.getServiceName(),info.getRestPackage(),info.getServicePackage());
            executeService("Service.java.vm",info.getServiceName(), serviceInfoList,info.getServicePackage());
            executeServiceImpl("ServiceImpl.java.vm",info.getServiceName()+"Impl", serviceInfoList,info.getServiceName(),info.getServicePackage());
            serviceInfoList.clear();
        }
    }



    public static void executeFrom(String templatePath, String className, List<FromInfo> list,String fromPackage){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/"+fromPackage.replaceAll("\\.","/");
        File file = new File(projectPath+"/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package",fromPackage);
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

    public static void executeService(String templatePath, String className, List<ServiceInfo> list,String servicePackage){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/"+servicePackage.replaceAll("\\.","/");
        File file = new File(projectPath+"/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package",servicePackage);
        map.put("className",className);
        map.put("services",list);
        List<String> pojoPackage = list.stream().map(ServiceInfo::getPackages).flatMap(Arrays::stream).collect(Collectors.toList());
        map.put("pojoPackage",pojoPackage);
        outputFile(file,map, "template/"+templatePath,true);
    }

    public static void executeServiceImpl(String templatePath, String className, List<ServiceInfo> list,String serviceName,String servicePackage){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/"+servicePackage.replaceAll("\\.","/");
        File file = new File(projectPath+"/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package",servicePackage);
        map.put("className",className);
        map.put("services",list);
        map.put("serviceName",serviceName);
        List<String> pojoPackage = list.stream().map(ServiceInfo::getPackages).flatMap(Arrays::stream).collect(Collectors.toList());
        map.put("pojoPackage",pojoPackage);
        outputFile(file,map, "template/"+templatePath,true);
    }

    public static void executeRest(String templatePath, String className, List<ServiceInfo> list,String serviceName,String restPackage,String servicePackage){
        String projectPath = System.getProperty("user.dir")+"/src/main/java/"+restPackage.replaceAll("\\.","/");
        File file = new File(projectPath+"/"+className+".java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("package",restPackage);
        map.put("className",className);
        map.put("services",list);
        map.put("servicesName",serviceName);
        map.put("servicePackage",servicePackage);
        List<String> pojoPackage = list.stream().map(ServiceInfo::getPackages).flatMap(Arrays::stream).collect(Collectors.toList());
        map.put("pojoPackage",pojoPackage);
        outputFile(file,map, "template/"+templatePath,true);
    }


}
