package com.fzdkx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/18 11:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dish {
    private Long id;
    private String name;
    // 分类ID
    private Long categoryId;
    // 价格
    private BigDecimal price;
    // 图片地址
    private String image;
    // 描述信息
    private String description;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
    // 创建人
    private Long createUser;
    // 修改人
    private Long updateUser;
}
