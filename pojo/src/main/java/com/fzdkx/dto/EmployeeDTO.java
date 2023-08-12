package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 发着呆看星
 * @create 2023/8/12 9:19
 */
@Data
@ApiModel(description = "新增员工的数据模型")
public class EmployeeDTO {
    @ApiModelProperty("身份证")
    private String idNumber;
    @ApiModelProperty("员工姓名")
    private String name;
    @ApiModelProperty("员工手机号码")
    private String phone;
    @ApiModelProperty("员工性别")
    private String sex;
    @ApiModelProperty("员工用户名")
    private String username;
}
