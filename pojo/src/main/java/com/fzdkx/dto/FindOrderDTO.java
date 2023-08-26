package com.fzdkx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/26 15:17
 */
@ApiModel(discriminator = "搜索订单数据模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderDTO {
    @ApiModelProperty("第几页")
    private Integer page;
    @ApiModelProperty("每页记录条数")
    private Integer pageSize;
    @ApiModelProperty("开始时间")
    private LocalDateTime beginTime;
    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty("订单编号")
    private String number;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("订单状态")
    private Integer status;
}
