package com.fzdkx.service.impl;

import com.fzdkx.dto.SetmealEditDTO;
import com.fzdkx.dto.SetmealPageQueryDTO;
import com.fzdkx.entity.Setmeal;
import com.fzdkx.entity.SetmealDish;
import com.fzdkx.exception.SqlException;
import com.fzdkx.mapper.SetmealDishMapper;
import com.fzdkx.mapper.SetmealMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.SetmealService;
import com.fzdkx.vo.SetmealPageQueryVO;
import com.fzdkx.vo.SetmealVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.fzdkx.constant.MessageConstant.DISH_ISNULL_ERROR;

/**
 * @author 发着呆看星
 * @create 2023/8/20 11:44
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealDishMapper setmealDishMapper;

    @Override
    public PageResult<SetmealPageQueryVO > pageQuerySetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        Setmeal setmeal = Setmeal.builder()
                .name(setmealPageQueryDTO.getName())
                .categoryId(setmealPageQueryDTO.getCategoryId())
                .status(setmealPageQueryDTO.getStatus()).build();
        // 设置分页参数
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        // 查询套餐
        List<SetmealPageQueryVO > setmeals = setmealMapper.pageSelectSetmeal(setmeal);
        // 获取分页数据
        PageInfo<SetmealPageQueryVO > pageInfo = new PageInfo<>(setmeals);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    @Transactional
    public void addSetmeal(SetmealVO setmealVO) {
        // 获取套餐信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealVO,setmeal);
        // 新增套餐信息
        try {
            setmealMapper.insertSetmeal(setmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取套餐菜品信息
        List<SetmealDish> setmealDishes = setmealVO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        // 新增套餐菜品信息
        if (setmealDishes.size() == 0){
           throw new SqlException(DISH_ISNULL_ERROR);
        }
        setmealDishMapper.insertSetmealDish(setmealDishes);
    }

    @Override
    public SetmealVO findSetmeal(Long id) {
        // 查询套餐信息
        SetmealPageQueryVO setmealPageQueryVO = setmealMapper.selectSetmealById(id);
        if (setmealPageQueryVO == null){
            throw new SqlException("套餐不存在");
        }
        // 查询对应菜品信息
        List<SetmealDish> setmealDishes = setmealDishMapper.selectBySetmealId(id);
        // 封装成 SetmealVO
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmealPageQueryVO,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public void cancelSetmeal(List<Long> ids) {
        // 起售中的套餐不能删除
        for (Long id : ids) {
            Integer status = setmealMapper.selectSetmealStatus(id);
            if (status == 1){
                throw new SqlException("起售中的套餐不能被删除");
            }
        }
        // 删除套餐
        setmealMapper.deleteSetmeal(ids);
        // 删除套餐与菜品之间的关联
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    @Override
    public void alterSetmealStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                                .status(status)
                                .id(id).build();
        try {
            setmealMapper.updateSetmealStatusById(setmeal);
        } catch (Exception e) {
            throw new SqlException("套餐不存在");
        }
    }

    @Override
    @Transactional
    public void alterSetmeal(SetmealEditDTO setmealEditDTO) {
        // 修改套餐
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealEditDTO,setmeal);
        try {
            setmealMapper.updateSetmeal(setmeal);
        } catch (Exception e) {
            throw new SqlException("该套餐不存在");
        }
        // 删除套餐相关菜品信息
        Long id = setmealEditDTO.getId();
        setmealDishMapper.deleteBySetmealId(id);
        // 增加套餐相关菜品信息
        List<SetmealDish> setmealDishes = setmealEditDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
        }
        setmealDishMapper.insertSetmealDish(setmealDishes);
    }
}