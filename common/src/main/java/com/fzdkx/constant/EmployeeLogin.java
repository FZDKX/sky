package com.fzdkx.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/21 11:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLogin {
    private Long id;
    private String name;
    private String role;
}
