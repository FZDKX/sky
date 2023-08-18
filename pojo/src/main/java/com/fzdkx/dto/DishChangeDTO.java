package com.fzdkx.dto;

import com.fzdkx.entity.Flavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 12:28
 */
@ApiModel(discriminator = "修改菜品数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishChangeDTO {
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("菜品ID")
    private Long id;
    @ApiModelProperty("菜品图片地址")
    private String image;
    @ApiModelProperty("菜品名称")
    private String name;
    @ApiModelProperty("菜品价格")
    private BigDecimal price;
    @ApiModelProperty("菜品口味")
    private List<Flavor> flavors;
    @ApiModelProperty("菜品描述")
    private String description;
    @ApiModelProperty("菜品状态")
    private Integer status;
}
