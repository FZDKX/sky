package com.fzdkx.mapper;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
import com.fzdkx.entity.Flavor;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 13:03
 */
public interface FlavorMapper {
    void deleteFlavorByIds(List<Long> ids);

    void deleteFlavorByDishIds(List<Long> ids);

    void insertFlavors(List<Flavor> flavors);

    List<Flavor> selectFlavorsByDishId(@Param("dishId") Long dishId);
}
