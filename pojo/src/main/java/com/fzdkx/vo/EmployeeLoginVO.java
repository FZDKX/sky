package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/10 15:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "员工登录返回的数据格式")
public class EmployeeLoginVO {
    @ApiModelProperty("员工id")
    private Long id;
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("姓名")
    private String username;
    @ApiModelProperty("jwt令牌")
    private String token;
}
