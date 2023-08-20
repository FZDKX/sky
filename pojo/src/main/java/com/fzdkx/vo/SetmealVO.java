package com.fzdkx.vo;

import com.fzdkx.entity.SetmealDish;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.ws.soap.Addressing;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/20 12:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "套餐菜品数据模型")
public class SetmealVO {
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("套餐名称")
    private String name;
    @ApiModelProperty("套餐图片地址")
    private String image;
    @ApiModelProperty("套餐价格")
    private BigDecimal price;
    @ApiModelProperty("套餐状态")
    private Integer status;
    @ApiModelProperty("套餐描述")
    private String description;
    @ApiModelProperty("套餐ID")
    private Long id;
    @ApiModelProperty("套餐相关菜品信息")
    private List<SetmealDish> setmealDishes;
    @ApiModelProperty("分类名称")
    private String categoryName;

}
