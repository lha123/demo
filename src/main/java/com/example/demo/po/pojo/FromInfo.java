package com.example.demo.po.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FromInfo {

    private String title;
    private String fieldType;
    private String field;
    private String vailMessage = "";


}
