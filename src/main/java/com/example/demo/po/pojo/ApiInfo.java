package com.example.demo.po.pojo;


import lombok.Data;

import java.util.List;

@Data
public class ApiInfo {

    private String fromClass;
    private String voClass;
    //private String serviceClass;
    private List<FromInfo> fromList;
    private List<FromInfo> voList;
    private ServiceInfo serviceInfo;
    private String requestMode;
    private String title;
    private Integer isValid = 0;
    private String method;
    private Integer commitServiceMethod = 0;

}
