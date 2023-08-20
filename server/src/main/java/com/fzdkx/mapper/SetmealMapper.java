package com.fzdkx.mapper;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
import com.fzdkx.entity.Setmeal;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 16:08
 */
public interface SetmealMapper {

    @AutoFill(AutoFillType.UPDATE)
    void updateSetmealStatus(@Param("setmeal") Setmeal setmeal , @Param("setmealIds") List<Long> setmealIds);
}
