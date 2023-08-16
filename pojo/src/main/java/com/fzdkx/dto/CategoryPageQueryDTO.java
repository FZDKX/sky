package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:49
 */
@ApiModel(description = "分类分页查询数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPageQueryDTO {
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页记录条数")
    private Integer pageSize;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("分类类型")
    private Integer type;
}
