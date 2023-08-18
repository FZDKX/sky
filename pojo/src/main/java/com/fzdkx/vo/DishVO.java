package com.fzdkx.vo;

import com.fzdkx.entity.Flavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(discriminator = "菜品数据模型")
public class DishVO {
    @ApiModelProperty("菜品ID")
    private Long id;
    @ApiModelProperty("菜品名称")
    private String name;
    @ApiModelProperty("菜品价格")
    private BigDecimal price;
    @ApiModelProperty("菜品状态")
    private Integer status;
    @ApiModelProperty("菜品图片地址")
    private String image;
    @ApiModelProperty("菜品描述信息")
    private String description;
    @ApiModelProperty("菜品修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("分类ID")
    private Long categoryId;
}
