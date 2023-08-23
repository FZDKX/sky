package com.fzdkx.service.impl;

import com.fzdkx.constant.MessageConstant;
import com.fzdkx.dto.CartDTO;
import com.fzdkx.dto.GoodsCartDTO;
import com.fzdkx.entity.ShoppingCart;
import com.fzdkx.exception.ParamException;
import com.fzdkx.mapper.DishMapper;
import com.fzdkx.mapper.SetmealMapper;
import com.fzdkx.mapper.ShoppingCartMapper;
import com.fzdkx.service.ShoppingCartService;
import com.fzdkx.utils.UserThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 9:45
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private DishMapper dishMapper;

    @Resource
    private SetmealMapper setmealMapper;

    @Override
    public void addCart(CartDTO cartDTO) {
        Long setmealId = cartDTO.getSetmealId();
        Long dishId = cartDTO.getDishId();
        if ( (setmealId == null && dishId == null) || (setmealId != null && dishId != null)){
            throw new ParamException(MessageConstant.PARAM_IS_ERROR);
        }
        ShoppingCart cart = ShoppingCart.builder()
                .userId(UserThreadLocal.getId())
                .build();
        BeanUtils.copyProperties(cartDTO,cart);
        // 先查看当前用户是否添加相同购物车
        Integer count = shoppingCartMapper.selectCartExist(cart);

        // 如果有相同购物车，购物车数量加一
        if (count != null && count == 1){
            shoppingCartMapper.increNumber(cart);
            return;
        }

        // 如果没有相同购物车 ，则向购物车中添加商品
        if (dishId != null){
            // 查询菜品信息，并封装
            GoodsCartDTO goodsCartDTO = dishMapper.selectDishCartById(dishId);
            // 复制到cart中
            BeanUtils.copyProperties(goodsCartDTO,cart);
        }else {
            GoodsCartDTO goodsCartDTO = setmealMapper.selectSetmealCartById(setmealId);
            // 复制到cart中
            BeanUtils.copyProperties(goodsCartDTO,cart);
        }
        cart.setNumber(1);
        cart.setCreateTime(LocalDateTime.now());

        // 添加购物车
        shoppingCartMapper.insertCart(cart);
    }

    @Override
    public List<ShoppingCart> queryCartList() {
        Long userId = UserThreadLocal.getId();
        return shoppingCartMapper.selectCartList(userId);
    }

    @Override
    public void cleanCart() {
        shoppingCartMapper.deleteCartAll(UserThreadLocal.getId());
    }

    @Override
    public void cleanCartOne(CartDTO cartDTO) {
        ShoppingCart cart = ShoppingCart.builder()
                .userId(UserThreadLocal.getId())
                .build();
        BeanUtils.copyProperties(cartDTO,cart);

        // 查询购物车数量
        Integer number = shoppingCartMapper.selectNumber(cart);

        if (number == null || number == 1){
            shoppingCartMapper.deleteCartOne(cart);
            return;
        }
        shoppingCartMapper.reduceNumber(cart);
    }

}
