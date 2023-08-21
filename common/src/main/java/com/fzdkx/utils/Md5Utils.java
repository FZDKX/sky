package com.fzdkx.utils;

import org.springframework.util.DigestUtils;

/**
 * @author 发着呆看星
 * @create 2023/8/21 11:13
 */
public class Md5Utils {
    public static String encode(String code){
        return DigestUtils.md5DigestAsHex(code.getBytes());
    }
}
