package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/20 13:54
 */
@ApiModel(description = "分页查询返回数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetmealPageQueryVO {
    @ApiModelProperty("套餐id")
    private Long id;
    @ApiModelProperty("套餐名称")
    private String name;
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("套餐价格")
    private BigDecimal price;
    @ApiModelProperty("套餐状态")
    private Integer status;
    @ApiModelProperty("套餐描述")
    private String description;
    @ApiModelProperty("套餐图片地址")
    private String image;
    @ApiModelProperty("套餐修改时间")
    private LocalDateTime updateTime;
}
