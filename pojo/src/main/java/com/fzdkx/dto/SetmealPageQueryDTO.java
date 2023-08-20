package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/20 11:41
 */
@ApiModel(description = "分页查询套餐数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetmealPageQueryDTO {
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("套餐名称")
    private String name;
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页最大记录条数")
    private Integer pageSize;
    @ApiModelProperty("套餐状态")
    private Integer status;
}
