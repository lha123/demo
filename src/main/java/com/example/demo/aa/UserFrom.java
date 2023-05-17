package com.example.demo.aa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(value = "创建模型")
public class UserFrom  {

    @NotBlank(message = "用户名称不能为空！")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @NotBlank(message = "用户手机号不能为空！")
    @ApiModelProperty(value = "用户手机号")
    private String phone;
}
