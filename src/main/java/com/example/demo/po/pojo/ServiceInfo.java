package com.example.demo.po.pojo;

import lombok.Data;

@Data
public class ServiceInfo {
    private String requestMode;
    private String title;
    private Boolean isValid;
    private String fromUpperCase;
    private String fromLowerCase;
    private String vo;
    private String method;
    private String[] packages;

    public ServiceInfo(String title, String method) {
        this.title = title;
        this.method = method;
    }

}
