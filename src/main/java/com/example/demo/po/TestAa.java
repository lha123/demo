package com.example.demo.po;



import lombok.Data;
import javax.validation.constraints.NotNull;


@Data
public class TestAa {
    private Integer id;
    private String title;
    private String name;
    @NotNull
    private Integer age;
}
