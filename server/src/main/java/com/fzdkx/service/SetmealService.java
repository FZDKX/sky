package com.fzdkx.service;

import com.fzdkx.dto.SetmealEditDTO;
import com.fzdkx.dto.SetmealPageQueryDTO;
import com.fzdkx.entity.Setmeal;
import com.fzdkx.result.PageResult;
import com.fzdkx.vo.SetmealPageQueryVO;
import com.fzdkx.vo.SetmealVO;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/20 11:44
 */
public interface SetmealService {
    PageResult<SetmealPageQueryVO > pageQuerySetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void addSetmeal(SetmealVO setmealVO);

    SetmealVO findSetmeal(Long id);

    void cancelSetmeal(List<Long> ids);

    void alterSetmealStatus(Integer status, Long id);

    void alterSetmeal(SetmealEditDTO setmealEditDTO);

    List<Setmeal> querySetmealListByCategory(Long categoryId);

}
