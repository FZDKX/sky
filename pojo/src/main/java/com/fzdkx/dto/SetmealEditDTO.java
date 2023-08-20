package com.fzdkx.dto;

import com.fzdkx.entity.SetmealDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/20 15:10
 */
@ApiModel(description = "修改套餐数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetmealEditDTO {
    @ApiModelProperty("套餐ID")
    private Long id;
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("套餐描述")
    private String description;
    @ApiModelProperty("套餐图片地址")
    private String image;
    @ApiModelProperty("套餐名称")
    private String name;
    @ApiModelProperty("套餐价格")
    private BigDecimal price;
    @ApiModelProperty("套餐状态")
    private Integer status;
    @ApiModelProperty("套餐相关菜品")
    private List<SetmealDish> setmealDishes;
}
