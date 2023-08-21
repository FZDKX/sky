package com.fzdkx.mapper;

import com.fzdkx.entity.SetmealDish;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/20 10:46
 */
public interface SetmealDishMapper {
    // 根据菜品ID查询关联套餐ID
    public List<Long> selectDish(@Param("dishId") Long dishId);

    // 创建套餐菜品对应信息
    void insertSetmealDish(List<SetmealDish> setmealDishes);

    // 根据套餐ID查询对应记录
    List<SetmealDish> selectBySetmealId(@Param("setmealId") Long id);


    void deleteBySetmealIds(@Param("ids") List<Long> ids);


    void deleteBySetmealId(@Param("setmealId") Long id);
}
