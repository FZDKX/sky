package com.fzdkx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/28 11:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsVO {
    private String dateList;
    private String newUserList;
    private String totalUserList;
}
