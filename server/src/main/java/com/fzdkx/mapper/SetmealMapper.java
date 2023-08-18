package com.fzdkx.mapper;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 16:08
 */
public interface SetmealMapper {

    public List<Long> selectDish(@Param("dishId") Long dishId);
}
