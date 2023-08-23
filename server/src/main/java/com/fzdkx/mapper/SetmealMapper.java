package com.fzdkx.mapper;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
import com.fzdkx.dto.GoodsCartDTO;
import com.fzdkx.entity.Setmeal;
import com.fzdkx.vo.SetmealPageQueryVO;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 16:08
 */
public interface SetmealMapper {

    @AutoFill(AutoFillType.UPDATE)
    void updateSetmealStatus(@Param("setmeal") Setmeal setmeal , @Param("setmealIds") List<Long> setmealIds);

    List<SetmealPageQueryVO> pageSelectSetmeal(Setmeal setmeal);

    @AutoFill(AutoFillType.INSERT)
    void insertSetmeal(Setmeal setmeal);

    SetmealPageQueryVO selectSetmealById(Long id);

    Integer selectSetmealStatus(@Param("id") Long id);

    void deleteSetmeal(@Param("ids") List<Long> ids);

    @AutoFill(AutoFillType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    @AutoFill(AutoFillType.UPDATE)
    void updateSetmealStatusById(@Param("setmeal") Setmeal setmeal);

    List<Setmeal> selectSetmealList(Long categoryId);

    Integer selectSetmealByCategoryId(Integer id);

    GoodsCartDTO selectSetmealCartById(Long setmealId);

}
