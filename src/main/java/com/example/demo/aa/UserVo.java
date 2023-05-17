package com.example.demo.aa;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

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
    private Integer age;
}
