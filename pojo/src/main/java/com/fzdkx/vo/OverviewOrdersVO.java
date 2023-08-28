package com.fzdkx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/28 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverviewOrdersVO {
    private Integer allOrders;
    private Integer cancelledOrders;
    private Integer completedOrders;
    private Integer deliveredOrders;
    private Integer waitingOrders;
}
