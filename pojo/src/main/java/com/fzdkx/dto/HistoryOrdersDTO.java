package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/26 13:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(discriminator = "查询历史订单模型")
public class HistoryOrdersDTO {
    private Integer page;
    private Integer pageSize;
    private Integer status;
}
