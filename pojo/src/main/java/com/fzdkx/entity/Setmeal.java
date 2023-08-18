package com.fzdkx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/18 16:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setmeal {
    private Long id;
    private Long categoryId;
    private String name;
    private BigDecimal price;
    private Integer status;
    private String description;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
}
