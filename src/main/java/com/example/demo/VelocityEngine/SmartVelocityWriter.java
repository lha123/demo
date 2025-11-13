package com.example.demo.VelocityEngine;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SmartVelocityWriter {
    private VelocityEngine velocityEngine;

    public SmartVelocityWriter() {
        velocityEngine = new VelocityEngine();
        velocityEngine.init();
    }

    /**
     * 在特定标记处插入内容
     */
    public void insertAtMarker(String filePath, String templatePath,
                               VelocityContext context, String marker) {
        try {
            // 读取现有文件内容
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // 渲染新内容
            StringWriter sw = new StringWriter();
            velocityEngine.mergeTemplate(templatePath, "UTF-8", context, sw);
            String newContent = sw.toString();

            // 查找标记位置并插入
            boolean markerFound = false;
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.println(line);
                    if (line.contains(marker)) {
                        writer.println(newContent);
                        markerFound = true;
                    }
                }

                // 如果没找到标记，在文件末尾追加
                if (!markerFound) {
                    writer.println("// " + marker);
                    writer.println(newContent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换文件中的特定部分
     */
    public void replaceSection(String filePath, String templatePath,
                               VelocityContext context, String startMarker,
                               String endMarker) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            StringWriter sw = new StringWriter();
            velocityEngine.mergeTemplate(templatePath, "UTF-8", context, sw);
            String newContent = sw.toString();

            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                boolean inSection = false;
                boolean sectionReplaced = false;

                for (String line : lines) {
                    if (line.contains(startMarker)) {
                        inSection = true;
                        writer.println(line);
                        writer.println(newContent);
                        sectionReplaced = true;
                        continue;
                    }

                    if (line.contains(endMarker)) {
                        inSection = false;
                    }

                    if (!inSection) {
                        writer.println(line);
                    }
                }

                // 如果没找到标记段，在文件末尾添加
                if (!sectionReplaced) {
                    writer.println(startMarker);
                    writer.println(newContent);
                    writer.println(endMarker);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}