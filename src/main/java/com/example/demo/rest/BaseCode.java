package com.example.demo.rest;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class BaseCode {
    private static VelocityEngine velocityEngine;
    static {
        velocityEngine = init();
    }



    public static @NotNull VelocityEngine init() {
        Properties p = new Properties();
        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
        p.setProperty(Velocity.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
        p.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.name());
        p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
        return new VelocityEngine(p);

    }




    public static void outputFile(@NotNull File file, @NotNull Map<String, Object> objectMap, @NotNull String templatePath, boolean fileOverride) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }
                writer(objectMap, templatePath, file,fileOverride);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
    }


    public static void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile,boolean fileOverride) throws Exception {
        //service serviceImpl rest 已用的类
        if(fileOverride){
            appendToFile((String) objectMap.get("addTemplate"),(VelocityContext)objectMap.get("context"),outputFile.getAbsolutePath());
        }else{
            // DTO VO
            Template template = velocityEngine.getTemplate(templatePath, StandardCharsets.UTF_8.name());
            try (FileOutputStream fos = new FileOutputStream(outputFile);
                 OutputStreamWriter ow = new OutputStreamWriter(fos, StandardCharsets.UTF_8.name());
                 BufferedWriter writer = new BufferedWriter(ow)) {
                template.merge(new VelocityContext(objectMap), writer);
            }
        }
        System.out.println("模板:" + templatePath + ";  文件:" + outputFile);
    }







    @SneakyThrows
    public static void appendToFile(String templatePath, VelocityContext context,String filePath) {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        try (FileWriter writer = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            // 渲染模板
            StringWriter sw = new StringWriter();
            velocityEngine.mergeTemplate(templatePath, StandardCharsets.UTF_8.name(), context, sw);

            // 写入文件


            // 找到最后一个 } 的位置
            int lastBraceIndex = content.lastIndexOf('}');
            if (lastBraceIndex == -1) {
                throw new IOException("未找到结束大括号");
            }

            // 构造要插入的内容

            // 在最后一个 } 前插入内容

            String newContent = content.substring(0, lastBraceIndex) +
                    sw +
                    content.substring(lastBraceIndex);

            bufferedWriter.write(newContent);
            bufferedWriter.newLine(); // 可选：添加换行

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
