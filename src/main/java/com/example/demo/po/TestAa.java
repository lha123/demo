package com.example.demo.po;




import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@ApiModel(value = "BackUpConfQueryParam对象", description = "备单关键字-配置查询参数")
public class TestAa {
    @ApiModelProperty(value = "公告标题2")
    private Integer id;
    @ApiModelProperty(value = "公告标题3")
    private String title;
    @ApiModelProperty(value = "公告标题4")
    private String name;
    @NotNull
    private Integer age;
}
