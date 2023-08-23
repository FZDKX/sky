package com.fzdkx.service;

import com.fzdkx.dto.CartDTO;
import com.fzdkx.entity.ShoppingCart;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 9:44
 */
public interface ShoppingCartService {
    void addCart(CartDTO cartDTO);

    List<ShoppingCart> queryCartList();

    void cleanCart();

    void cleanCartOne(CartDTO cartDTO);
}
