package com.example.demo;

import cn.hutool.core.io.FileUtil;
import com.example.demo.pojo.TableInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodeGenerator2  extends BaseCode{

    public static void main(String[] args) {


        String projectPath = System.getProperty("user.dir")+"/src/main/java/com/example/demo";
        File file = new File(projectPath+"/aa/As.java");
        Map<String,Object> map = new ConcurrentHashMap<>();
        List<TableInfo> aa = new ArrayList<>();
        aa.add(new TableInfo("客户名称","String","name"));
        map.put("package","com.example.demo.aa");
        map.put("pojoName","As");
        map.put("table",aa);
        outputFile(file,map,"template/createParam.java.vm",true);


    }






}
