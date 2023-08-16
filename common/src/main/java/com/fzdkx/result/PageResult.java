package com.fzdkx.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/13 21:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页查询返回结果模型")
@Builder
public class PageResult<T> {
    @ApiModelProperty("总记录条数")
    private long total;
    @ApiModelProperty("当前页数据")
    private List<T> records;
}
