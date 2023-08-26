package com.fzdkx.mapper;

import com.fzdkx.dto.FindOrderDTO;
import com.fzdkx.dto.HistoryOrdersDTO;
import com.fzdkx.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/24 19:56
 */
public interface OrderMapper {
    void insert(Order order);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Order getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Order orders);

    /**
     * 查询订单超时的订单ID的集合
     */
    List<Long> selectPayTimout(LocalDateTime localDateTime);

    List<Long> selectDeliveryOrder(LocalDateTime localDateTime);

    Order selectOrder(Long id);

    List<Order> selectHistoryOrder(@Param("status") Integer status , @Param("userId") Long userId);

    List<Order> selectOrderList(FindOrderDTO findOrderDTO);

    Integer selectConfirmed();

    Integer selectDeliveryInProgress();

    Integer selectToBeConfirmed();
}
