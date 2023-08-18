package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/18 11:49
 */
@ApiModel(description = "菜品分页查询数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishPageQueryVO {
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页记录条数")
    private Integer pageSize;
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("菜品名称")
    private String name;
    @ApiModelProperty("菜品状态")
    private Integer status;
}
