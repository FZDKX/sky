package com.fzdkx.service.impl;

import com.fzdkx.entity.Dish;
import com.fzdkx.mapper.DishMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.DishService;
import com.fzdkx.vo.DishPageQueryVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 11:53
 */
@Service
public class DishServiceImpl implements DishService {
    @Resource
    private DishMapper dishMapper;

    @Override
    public PageResult<Dish> pageQueryDish(DishPageQueryVO dishPageQueryVO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageQueryVO,dish);
        PageHelper.startPage(dishPageQueryVO.getPage(),dishPageQueryVO.getPageSize());
        List<Dish> dishList = dishMapper.selectDishList(dish);
        PageInfo<Dish> dishPageInfo = new PageInfo<>(dishList);
        return new PageResult<>(dishPageInfo.getTotal(),dishPageInfo.getList());
    }
}
