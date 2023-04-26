package com.example.demo.rest;

import com.example.demo.po.pojo.ApiInfo;
import com.example.demo.po.pojo.FromInfo;
import com.example.demo.po.pojo.ServiceInfo;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ThymeleafRest extends BaseCode{


    @PostMapping(value = {"/addInfo"})
    public boolean index(@RequestBody ApiInfo apiInfo){
        //executeOneApi(apiInfo);
        System.out.println(apiInfo);
        return true;
    }

    public static void executeOneApi(ApiInfo info){
        executeFrom("From.java.vm",info.getFromClass(),info.getFromList());
        executeFrom("Vo.java.vm",info.getVoClass(),info.getVoList());
        executeService("Service.java.vm",info.getServiceClass(), Lists.newArrayList(info.getServiceInfo()));
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
