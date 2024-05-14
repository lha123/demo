package com.example.demo.uplod;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

@RestController
public class UploadFile {

    @PostMapping("/upload")
    public void uploadFile(@RequestPart(value = "file") MultipartFile file) throws Exception {
        String pre = FilenameUtils.getExtension(file.getOriginalFilename());
        String filePath = "/Users/liuhonger/spring-test/" + DateUtil.formatDate(DateUtil.date())+"/"+UUID.randomUUID() + "." + pre;
        FileUtil.mkParentDirs(filePath);
        file.transferTo(new File(filePath));
    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response) throws IOException {
        File file = new File("/Users/liuhonger/spring-test/2023-04-14/b1153c09-a93d-4fdb-9de3-613d95a1bc86.jpeg");
        try (FileInputStream fis = new FileInputStream(file);
             ServletOutputStream out = response.getOutputStream();
             WritableByteChannel channel = Channels.newChannel(out)) {

            String downFileName = URLEncoder.encode(file.getName(), "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + downFileName);
            response.setContentType("application/octet-stream");

            fis.getChannel().transferTo(0, fis.available(), channel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
