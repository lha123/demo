package com.example.demo.pojo;

import lombok.Data;

@Data
public class ServiceInfo {
    private Boolean isGet;
    private String title;
    private Boolean isValid;
    private String fromUpperCase;
    private String fromLowerCase;
    private String vo;
    private String method;

    public ServiceInfo(String title, String method) {
        this.title = title;
        this.method = method;
    }
}
