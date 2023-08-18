package com.fzdkx.annotion;

import com.fzdkx.constant.AutoFillType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 发着呆看星
 * @create 2023/8/17 20:44
 * 自动填充注解
 */
@Target(ElementType.METHOD) // 在方法上使用
@Retention(RetentionPolicy.RUNTIME)  // 可以在运行时保存，可以通过反射获取
public @interface AutoFill {
    // 枚举类型value
    AutoFillType value();
}
