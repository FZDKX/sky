package com.fzdkx.service;

import com.fzdkx.entity.Dish;
import com.fzdkx.result.PageResult;
import com.fzdkx.vo.DishPageQueryVO;

/**
 * @author 发着呆看星
 * @create 2023/8/18 11:53
 */
public interface DishService {
    PageResult<Dish> pageQueryDish(DishPageQueryVO dishPageQueryVO);

}
