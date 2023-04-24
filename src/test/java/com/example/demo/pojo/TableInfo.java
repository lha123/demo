package com.example.demo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableInfo {

    private String title;

    private String fieldType;

    private String field;
}
