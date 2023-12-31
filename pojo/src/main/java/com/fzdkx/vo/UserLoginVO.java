package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/21 10:16
 */
@ApiModel(description = "用户登录返回数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {
    @ApiModelProperty("用户ID")
    private Integer id;
    @ApiModelProperty("用户微信ID")
    private String openId;
    @ApiModelProperty("jwt令牌")
    private String token;
}
