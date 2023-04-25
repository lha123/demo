package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceInfo {

    private String mappingName;
    private String title;
    private String fromUpperCase;
    private String fromLowerCase;
    private String vo;
    private String method;

}
