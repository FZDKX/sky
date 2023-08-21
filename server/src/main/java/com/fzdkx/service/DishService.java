package com.fzdkx.service;

import com.fzdkx.vo.DishAndFlavorVO;
import com.fzdkx.result.PageResult;
import com.fzdkx.dto.DishChangeDTO;
import com.fzdkx.vo.DishItemVO;
import com.fzdkx.vo.DishPageQueryVO;
import com.fzdkx.vo.DishVO;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 11:53
 */
public interface DishService {
    PageResult<DishVO> pageQueryDish(DishPageQueryVO dishPageQueryVO);

    void changeDish(DishChangeDTO dishChangeDTO);

    List<DishVO> queryDishListByCategoryId(Long categoryId);

    DishAndFlavorVO queryDishAnFlavorById(Long id);

    void saveDish(DishChangeDTO dishChangeDTO);

    void changeDishStatus(Integer status, Long id);

    void remove(List<Long> ids);

    List<DishItemVO> queryDishItemList(Long id);

    List<DishAndFlavorVO> queryDishAnFlavorByCategoryId(Long categoryId);

}
