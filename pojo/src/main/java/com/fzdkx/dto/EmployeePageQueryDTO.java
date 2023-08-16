package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 发着呆看星
 * @create 2023/8/12 13:55
 * 员工分页查询DTO
 */
@Data
@ApiModel(description = "员工分页查询数据模型")
public class EmployeePageQueryDTO {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页记录条数")
    private Integer pageSize;
}
