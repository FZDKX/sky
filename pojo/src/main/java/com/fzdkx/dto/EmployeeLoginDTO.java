package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 发着呆看星
 * @create 2023/8/10 16:12
 */
@ApiModel(description = "员工登录时的数据格式")
@Data
public class EmployeeLoginDTO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
