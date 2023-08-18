package com.fzdkx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/18 12:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flavor {
    // ID
    private Long id;
    // 菜品ID
    private Long dishId;
    // 口味名称
    private String name;
    // 口味值
    private String value;
}
