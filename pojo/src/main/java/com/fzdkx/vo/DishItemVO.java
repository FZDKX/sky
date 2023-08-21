package com.fzdkx.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/21 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "菜品描述")
public class DishItemVO {
    @ApiModelProperty("菜品份数")
    private Integer copies;
    @ApiModelProperty("菜品描述信息")
    private String description;
    @ApiModelProperty("菜品图片地址")
    private String image;
    @ApiModelProperty("菜品名称")
    private String name;
}
