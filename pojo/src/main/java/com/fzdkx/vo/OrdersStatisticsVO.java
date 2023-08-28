package com.fzdkx.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/28 11:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(discriminator = "订单统计数据返回模型")
public class OrdersStatisticsVO {
    @ApiModelProperty("日期列表")
    private String dateList;
    @ApiModelProperty("日期对应总订单数列表")
    private String orderCountList;
    @ApiModelProperty("日期对应有效订单数列表")
    private String validOrderCountList;
    @ApiModelProperty("订单完成率")
    private Double orderCompletionRate;
    @ApiModelProperty("订单总数")
    private Integer totalOrderCount;
    @ApiModelProperty("有效订单总数")
    private Integer validOrderCount;
}
