package com.example.demo.po;



import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TestAa {
    private Integer id;
    private String title;
    private String name;
    @NotNull
    private Integer age;
}
