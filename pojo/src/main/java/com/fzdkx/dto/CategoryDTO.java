package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/16 21:08
 */
@ApiModel(description = "分类数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @ApiModelProperty("分类ID")
    private Long id;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("分类类型")
    private Integer type;
}
