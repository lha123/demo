package com.example.demo.VelocityEngine;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JavaFileGenerator {
    private VelocityEngine velocityEngine;
    private String outputPath;

    public JavaFileGenerator(String outputPath) {
        this.outputPath = outputPath;
        this.velocityEngine = new VelocityEngine();
        velocityEngine.init();

        // 确保输出目录存在
        new File(outputPath).getParentFile().mkdirs();
    }

    /**
     * 生成 Java 类文件
     */
    public void generateJavaClass(String className, String packageName,
                                  List<String> imports, List<Field> fields,
                                  List<Method> methods) {
        VelocityContext context = new VelocityContext();
        context.put("className", className);
        context.put("packageName", packageName);
        context.put("imports", imports);
        context.put("fields", fields);
        context.put("methods", methods);

        // 渲染类模板
        StringWriter sw = new StringWriter();
        velocityEngine.mergeTemplate("templates/JavaClass.vm", "UTF-8", context, sw);

        // 写入文件
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(sw.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向现有类添加方法
     */
    public void addMethodToClass(String className, Method newMethod) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(outputPath));
            List<String> newLines = new ArrayList<>();

            boolean inClass = false;
            int braceCount = 0;
            int lastBraceIndex = -1;

            // 分析文件结构
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                newLines.add(line);

                if (line.contains("class " + className)) {
                    inClass = true;
                }

                if (inClass) {
                    if (line.contains("{")) braceCount++;
                    if (line.contains("}")) braceCount--;

                    if (braceCount == 1 && line.trim().equals("}")) {
                        lastBraceIndex = i;
                    }
                }
            }

            // 在最后一个 } 前插入新方法
            if (lastBraceIndex != -1) {
                VelocityContext context = new VelocityContext();
                context.put("method", newMethod);

                StringWriter sw = new StringWriter();
                velocityEngine.mergeTemplate("templates/MethodTemplate.vm", "UTF-8", context, sw);

                // 重新构建文件内容
                List<String> finalLines = new ArrayList<>();
                for (int i = 0; i < lines.size(); i++) {
                    if (i == lastBraceIndex) {
                        finalLines.add("\t" + sw.toString().trim()); // 添加新方法
                        finalLines.add(""); // 空行
                    }
                    finalLines.add(lines.get(i));
                }

                // 写回文件
                try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
                    for (String line : finalLines) {
                        writer.println(line);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 辅助类
    @Data
    @AllArgsConstructor
    public static class Field {
        private String type;
        private String name;
        private String visibility;

        // constructors, getters, setters
    }
    @Data
    @AllArgsConstructor
    public static class Method {
        private String returnType;
        private String name;
        private List<Parameter> parameters;
        private String body;
        private String visibility;

        // constructors, getters, setters
    }
    @Data
    @AllArgsConstructor
    public static class Parameter {
        private String type;
        private String name;

        // constructors, getters, setters
    }
}