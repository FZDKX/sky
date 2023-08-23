package com.fzdkx.mapper;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
import com.fzdkx.dto.GoodsCartDTO;
import com.fzdkx.vo.DishAndFlavorVO;
import com.fzdkx.entity.Dish;
import com.fzdkx.vo.DishItemVO;
import com.fzdkx.vo.DishVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 12:03
 */
public interface DishMapper {

    List<DishVO> selectDishList(Dish dish);

    @AutoFill(AutoFillType.UPDATE)
    void updateDish(Dish dish);

    DishAndFlavorVO selectDishAndFlavorById(@Param("id") Long id);

    @AutoFill(AutoFillType.INSERT)
    void insertDish(Dish dish);

    Integer selectDishStatus(@Param("id") Long id);

    void deleteDish(List<Long> ids);

    List<DishItemVO> selectDishListBySetmealId(Long setmealId);

    List<DishAndFlavorVO> selectDishAndFlavorByCategoryId(@Param("categoryId") Long categoryId);

    Integer selectDishByCategoryId(Integer id);

    GoodsCartDTO selectDishCartById(Long dishId);
}
