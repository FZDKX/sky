package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/24 19:52
 */
@ApiModel(discriminator = "订单提交返回数据模型")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderSubmitVO {
    @ApiModelProperty("订单ID")
    private Long id;
    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;
    @ApiModelProperty("订单编号")
    private String orderNumber;
    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;
}
