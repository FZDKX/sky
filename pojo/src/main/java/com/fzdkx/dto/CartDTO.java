package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/23 9:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "添加购物车DTO")
public class CartDTO {
    private String dishFlavor;
    private Long dishId;
    private Long setmealId;
}
