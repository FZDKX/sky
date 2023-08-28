package com.fzdkx.vo;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.PhantomReference;

/**
 * @author 发着呆看星
 * @create 2023/8/28 13:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Top10VO {
    private String nameList;
    private String numberList;
}
