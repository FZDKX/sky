package com.fzdkx.mapper;

import com.fzdkx.entity.ShoppingCart;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 9:46
 */
public interface ShoppingCartMapper {
    Integer selectCartExist(ShoppingCart cart);

    void insertCart(ShoppingCart cart);

    void increNumber(ShoppingCart cart);

    List<ShoppingCart> selectCartList(Long userId);

    void deleteCartAll(Long id);

    void deleteCartOne(ShoppingCart cart);

    Integer selectNumber(ShoppingCart cart);

    void reduceNumber(ShoppingCart cart);
}
