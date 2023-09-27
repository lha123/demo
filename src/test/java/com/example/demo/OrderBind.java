package com.example.demo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class OrderBind {

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "关联订单id（关联的订单id/已变更订单id）")
    private Long bindOrderId;
}
