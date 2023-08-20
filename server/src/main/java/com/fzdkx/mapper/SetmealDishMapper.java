package com.fzdkx.mapper;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/20 10:46
 */
public interface SetmealDishMapper {
    public List<Long> selectDish(@Param("dishId") Long dishId);
}
