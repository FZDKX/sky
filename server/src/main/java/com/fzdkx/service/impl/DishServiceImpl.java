package com.fzdkx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fzdkx.dto.DishChangeDTO;
import com.fzdkx.entity.Dish;
import com.fzdkx.entity.Flavor;
import com.fzdkx.entity.Setmeal;
import com.fzdkx.exception.SqlException;
import com.fzdkx.mapper.DishMapper;
import com.fzdkx.mapper.FlavorMapper;
import com.fzdkx.mapper.SetmealDishMapper;
import com.fzdkx.mapper.SetmealMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.DishService;
import com.fzdkx.vo.DishAndFlavorVO;
import com.fzdkx.vo.DishItemVO;
import com.fzdkx.vo.DishPageQueryVO;
import com.fzdkx.vo.DishVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.fzdkx.constant.RedisConstant.*;
import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;

/**
 * @author 发着呆看星
 * @create 2023/8/18 11:53
 */
@Service
public class DishServiceImpl implements DishService {
    @Resource
    private DishMapper dishMapper;
    @Resource
    private FlavorMapper flavorMapper;
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealDishMapper setmealDishMapper;
    @Resource
    private StringRedisTemplate template;

    @Override
    public PageResult<DishVO> pageQueryDish(DishPageQueryVO dishPageQueryVO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageQueryVO, dish);
        PageHelper.startPage(dishPageQueryVO.getPage(), dishPageQueryVO.getPageSize());
        List<DishVO> dishList = dishMapper.selectDishList(dish);
        PageInfo<DishVO> dishPageInfo = new PageInfo<>(dishList);
        return new PageResult<>(dishPageInfo.getTotal(), dishPageInfo.getList());
    }

    @Override
    @Transactional
    public void changeDish(DishChangeDTO dishChangeDTO) {
        // 修改菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishChangeDTO, dish);
        dishMapper.updateDish(dish);
        // 获得口味集合，修改口味
        List<Flavor> flavors = dishChangeDTO.getFlavors();
        // 修改口味之前要删除菜品原本的口味
        List<Long> idList = flavors.stream()
                .map(Flavor::getId)
                .collect(Collectors.toList());
        if (idList.size() != 0) {
            flavorMapper.deleteFlavorByIds(idList);
        }
        // 修改口味
        if (flavors.size() != 0) {
            flavorMapper.insertFlavors(flavors);
        }
    }

    @Override
    public List<DishVO> queryDishListByCategoryId(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        return dishMapper.selectDishList(dish);
    }

    @Override
    public DishAndFlavorVO queryDishAnFlavorById(Long id) {
        // 查询菜品信息
        DishAndFlavorVO dish = dishMapper.selectDishAndFlavorById(id);
        // 根据菜品信息查询口味信息
        List<Flavor> flavorList = flavorMapper.selectFlavorsByDishId(id);
        // 封装
        dish.setFlavors(flavorList);
        return dish;
    }

    @Override
    @Transactional
    public void saveDish(DishChangeDTO dishChangeDTO) {
        Dish dish = new Dish();
        dish.setStatus(DEFAULT_STATUS);
        BeanUtils.copyProperties(dishChangeDTO, dish);
        // 添加菜品
        dishMapper.insertDish(dish);
        // 添加口味
        List<Flavor> flavors = dishChangeDTO.getFlavors();
        flavors.forEach(flavor -> {
            flavor.setDishId(dish.getId());
        });
        flavorMapper.insertFlavors(flavors);
    }

    @Override
    public void changeDishStatus(Integer status, Long id) {
        // 修改菜品状态
        Dish dish = Dish.builder()
                .status(status)
                .id(id).build();
        dishMapper.updateDish(dish);
        // 查询菜品关联套餐
        List<Long> setmealIds = setmealDishMapper.selectDish(id);
        // 修改菜品关联套餐状态
        Setmeal setmeal = Setmeal.builder()
                .status(status).build();
        setmealMapper.updateSetmealStatus(setmeal, setmealIds);
    }

    @Override
    @Transactional
    public void remove(List<Long> ids) {
        // 在售菜品不能删除
        boolean flag = true;
        for (Long id : ids) {
            Integer status = dishMapper.selectDishStatus(id);
            if (status == 1) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            throw new SqlException("删除菜品还未停售，不能删除");
        }
        // 与套餐关联菜品不能删除
        for (Long dishId : ids) {
            List<Long> setmealIds = setmealDishMapper.selectDish(dishId);
            if (setmealIds != null && setmealIds.size() != 0) {
                flag = false;
                break;
            }
        }
        if (!flag) {
            throw new SqlException("删除菜品已绑定套餐，不能删除");
        }
        // 删除菜品
        dishMapper.deleteDish(ids);
        // 删除菜品相关口味
        flavorMapper.deleteFlavorByDishIds(ids);
    }

    @Override
    public List<DishItemVO> queryDishItemList(Long id) {
        // 先从缓存中查找
        String json = template.opsForValue().get(DISH_SETMEAL_PREFIX + id);

        // 如果缓存中有，直接返回
        if (json != null) {
            return JSONObject.parseArray(json, DishItemVO.class);
        }
        // 查询数据库
        List<DishItemVO> dishItemVOList = dishMapper.selectDishListBySetmealId(id);

        // 重建缓存
        json = JSONObject.toJSONString(dishItemVOList);
        template.opsForValue().set(DISH_SETMEAL_PREFIX + id, json);

        return dishItemVOList;
    }

    @Override
    public List<DishAndFlavorVO> queryDishAnFlavorByCategoryId(Long categoryId) {

        // 先从缓存中查找
        String json = template.opsForValue().get(DISH_CATEGORY_PREFIX + categoryId);

        // 如果缓存中有，直接返回
        if (json != null) {
            return JSONObject.parseArray(json, DishAndFlavorVO.class);
        }
        // 如果缓存中没有，查询菜品信息
        List<DishAndFlavorVO> dishes = dishMapper.selectDishAndFlavorByCategoryId(categoryId);
        if (dishes == null || dishes.size() == 0) {
            throw new SqlException("该分类下没有菜品");
        }
        // 根据菜品信息查询口味信息并封装
        for (DishAndFlavorVO dish : dishes) {
            List<Flavor> flavorList = flavorMapper.selectFlavorsByDishId(dish.getId());
            // 封装
            dish.setFlavors(flavorList);
        }

        // 重建缓存
        json = JSONObject.toJSONString(dishes);
        template.opsForValue().set(DISH_CATEGORY_PREFIX + categoryId, json);
        return dishes;
    }
}
