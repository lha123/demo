package com.example.demo.aa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



/**
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(value="CustomerFrom对象", description="App建议反馈附件查询参数")
public class CustomerFrom  {

    @NotBlank(message = "用户名称不能为空！")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @NotBlank(message = "用户手机号不能为空！")
    @ApiModelProperty(value = "用户手机号")
    private String phone;
}
