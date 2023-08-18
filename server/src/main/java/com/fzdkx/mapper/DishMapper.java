package com.fzdkx.mapper;

import com.fzdkx.entity.Dish;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 12:03
 */
public interface DishMapper {

    List<Dish> selectDishList(Dish dish);
}
