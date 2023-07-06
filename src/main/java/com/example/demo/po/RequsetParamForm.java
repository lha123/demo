package com.example.demo.po;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(description = "a") //重要 没有这个 swagger 显示有问题
public class RequsetParamForm {


    public interface InsertRest {};
    public interface UpdateRest {};

    @JsonView(value = {UpdateRest.class})
    private Integer id;

    @JsonView(value = {InsertRest.class,UpdateRest.class})
    public String name;

    @JsonView(value = {InsertRest.class,UpdateRest.class})
    private Integer age;

    @JsonView(value = {InsertRest.class})
    private String email;
}
