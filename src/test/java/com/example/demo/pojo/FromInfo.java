package com.example.demo.pojo;


import lombok.Data;

@Data
public class FromInfo {

    private String title;
    private String fieldType;
    private String field;
    private String vailMessage = "";

    public FromInfo(String title, String fieldType, String field) {
        this.title = title;
        this.fieldType = fieldType;
        this.field = field;
    }

    public FromInfo(String title, String fieldType, String field, String vailMessage) {
        this.title = title;
        this.fieldType = fieldType;
        this.field = field;
        this.vailMessage = vailMessage;
    }
}
