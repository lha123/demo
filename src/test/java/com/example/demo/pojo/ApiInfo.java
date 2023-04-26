package com.example.demo.pojo;


import lombok.Data;

import java.util.List;

@Data
public class ApiInfo {

    private String fromClass;
    private List<FromInfo> fromList;
    private String voClass;
    private List<FromInfo> voList;
    private String serviceClass;
    private ServiceInfo serviceInfo;
}
