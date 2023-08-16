package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/16 12:01
 * 修改员工信息数据模型
 */
@ApiModel(description = "修改员工信息数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEditVO {
    @ApiModelProperty("ID")
    private Long id;
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
