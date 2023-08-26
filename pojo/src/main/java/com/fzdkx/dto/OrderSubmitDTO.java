package com.fzdkx.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/24 19:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "提交订单数据模型")
public class OrderSubmitDTO {
    @ApiModelProperty("地址簿ID")
    private Long addressBookId;
    @ApiModelProperty("总金额")
    private BigDecimal amount;
    @ApiModelProperty("配送状态")
    private Integer deliveryStatus;
    @ApiModelProperty("预计送达时间")
    private LocalDateTime estimateDeliveryTime;
    @ApiModelProperty("打包费")
    private Integer packAmount;
    @ApiModelProperty("付款方式")
    private Integer payMethod;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("餐具数量")
    private Integer tablewareNumber;
    @ApiModelProperty("餐具数量状态")
    private Integer tablewareStatus;
}
