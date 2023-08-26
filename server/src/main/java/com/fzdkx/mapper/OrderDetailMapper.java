package com.fzdkx.mapper;

import com.fzdkx.entity.OrderDetail;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/24 20:59
 */
public interface OrderDetailMapper {
    void insertList(@Param("list") ArrayList<OrderDetail> orderDetails);

    List<OrderDetail> selectByOrderId(Long orderId);
}
