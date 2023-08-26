package com.fzdkx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/26 17:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVO {
    private Integer confirmed;
    private Integer deliveryInProgress;
    private Integer toBeConfirmed;
}
