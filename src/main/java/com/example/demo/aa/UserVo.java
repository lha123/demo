package com.example.demo.aa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(value = "创建模型")
public class UserVo  {
    @ApiModelProperty(value = "用户名称")
    private String customerName;
    @ApiModelProperty(value = "用户年级")
    @NotNull(message = "ddd")
    private Integer age;
    @ApiModelProperty(value = "dfd")
    @Digits(integer=3,fraction = 1,message = "整数位上限为3位，小数位上限为2位")
    private String decimal;

}
