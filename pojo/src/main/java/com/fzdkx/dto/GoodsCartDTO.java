package com.fzdkx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 发着呆看星
 * @create 2023/8/23 10:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCartDTO {
    private Long id;
    private String image;
    private String name;
    private BigDecimal amount;
}
