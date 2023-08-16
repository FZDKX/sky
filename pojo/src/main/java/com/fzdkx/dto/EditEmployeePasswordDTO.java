package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/16 14:24
 */
@ApiModel(description = "修改用户密码数据模型")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditEmployeePasswordDTO {
    @ApiModelProperty("新密码")
    private String newPassword;
    @ApiModelProperty("旧密码")
    private String oldPassword;
}
