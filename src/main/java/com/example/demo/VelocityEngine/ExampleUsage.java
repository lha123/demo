package com.example.demo.VelocityEngine;

import com.example.demo.po.pojo.FromInfo;
import com.google.common.collect.Lists;
import org.apache.velocity.VelocityContext;

public class ExampleUsage {
    public static void main(String[] args) {
        // 方法1: 简单追加
        VelocityTemplateWriter writer = new VelocityTemplateWriter();
        writer.setFilePath("/Users/lha/test/demo/src/main/java/com/example/demo/aa/AddUser.java");
        //writer.initFile(); // 清空或创建文件
        VelocityContext context1 = new VelocityContext();
        context1.put("fields", Lists.newArrayList(new FromInfo("年纪信息","Integer","age","年纪不能为空!")));
        writer.appendToFile("template/AddReq.java.vm", context1);

        // 方法2: 在标记处插入
//        SmartVelocityWriter smartWriter = new SmartVelocityWriter();
//        VelocityContext context2 = new VelocityContext();
//        context2.put("methodName", "newMethod");
//        smartWriter.insertAtMarker("Output.java", "methodTemplate.vm",
//                context2, "// METHODS_PLACEHOLDER");
//
//        // 方法3: 代码生成器
//        JavaFileGenerator generator = new JavaFileGenerator("src/com/example/MyClass.java");
//
//        List<JavaFileGenerator.Field> fields = Arrays.asList(
//                new JavaFileGenerator.Field("String", "name", "private"),
//                new JavaFileGenerator.Field("int", "age", "private")
//        );
//
//        generator.generateJavaClass("MyClass", "com.example",
//                Arrays.asList("java.util.*"), fields,
//                new ArrayList<>());
    }
}
