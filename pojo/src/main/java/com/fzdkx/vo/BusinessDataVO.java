package com.fzdkx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 发着呆看星
 * @create 2023/8/28 14:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO {
    private Integer newUsers;
    private Double orderCompletionRate;
    private BigDecimal turnover;
    private BigDecimal unitPrice;
    private Integer validOrderCount;
}
