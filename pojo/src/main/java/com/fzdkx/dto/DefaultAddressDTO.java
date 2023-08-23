package com.fzdkx.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/23 14:54
 */
@ApiModel(description = "修改默认地址DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultAddressDTO {
    @ApiModelProperty("id")
    private Long id;
}
