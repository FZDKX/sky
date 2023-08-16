package com.fzdkx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/15 21:31
 */
@ApiModel(description = "查询员工信息返回模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePageQueryVO {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("最后修改时间")
    private LocalDateTime updateTime;

}
