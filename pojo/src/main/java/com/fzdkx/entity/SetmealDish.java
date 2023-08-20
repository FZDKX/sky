package com.fzdkx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 发着呆看星
 * @create 2023/8/20 12:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetmealDish {
    private Long id;
    // 套餐ID
    private Long setmealId;
    // 菜品ID
    private Long dishId;
    // 菜品名称
    private String name;
    // 菜品价格
    private BigDecimal price;
    // 份数
    private Integer copies;
}
