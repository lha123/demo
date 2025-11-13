package com.example.demo.VelocityEngine;

import com.example.demo.rest.BaseCode;
import lombok.SneakyThrows;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VelocityTemplateWriter {
    private VelocityEngine velocityEngine;
    private String filePath;

    public VelocityTemplateWriter() {
        velocityEngine = BaseCode.init();
    }

    /**
     * 追加内容到文件
     */
    @SneakyThrows
    public void appendToFile(String templatePath, VelocityContext context) {
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

    /**
     * 初始化文件（如果文件不存在则创建，存在则清空）
     */
    public void initFile() {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            // 创建空文件或清空已有内容
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // setter methods
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}