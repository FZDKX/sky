package com.fzdkx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 发着呆看星
 * @create 2023/8/23 14:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressBook {
    private Long id;
    private Long userId;
    private String consignee;
    private String sex;
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;

}
