package com.example.demo.rest;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import javax.validation.constraints.NotNull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class BaseCode {




    public static void outputFile(@NotNull File file, @NotNull Map<String, Object> objectMap, @NotNull String templatePath, boolean fileOverride) {
        if (isCreate(file, fileOverride)) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }
                writer(objectMap, templatePath, file);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    protected static boolean isCreate(@NotNull File file, boolean fileOverride) {
        if (file.exists() && !fileOverride) {
            System.out.println("文件[{}]已存在，且未开启文件覆盖配置，需要开启配置可到策略配置中设置！！！");
        }
        return !file.exists() || fileOverride;
    }


    public static void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        Template template = init().getTemplate(templatePath, utf);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, utf);
             BufferedWriter writer = new BufferedWriter(ow)) {
            template.merge(new VelocityContext(objectMap), writer);
        }
        System.out.println("模板:" + templatePath + ";  文件:" + outputFile);
    }

    public static @NotNull VelocityEngine init() {
            Properties p = new Properties();
            p.setProperty(VM_LOAD_PATH_KEY, VM_LOAD_PATH_VALUE);
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, utf);
            p.setProperty(Velocity.INPUT_ENCODING, utf);
            p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
            return new VelocityEngine(p);

    }

    static String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    static String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";
    static  String utf  =   StandardCharsets.UTF_8.name();


}
