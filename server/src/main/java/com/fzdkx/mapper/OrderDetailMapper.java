package com.fzdkx.mapper;

import com.fzdkx.dto.GoodsSalesDTO;
import com.fzdkx.entity.OrderDetail;
import io.lettuce.core.dynamic.annotation.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2023/8/24 20:59
 */
public interface OrderDetailMapper {
    void insertList(@Param("list") ArrayList<OrderDetail> orderDetails);

    List<OrderDetail> selectByOrderId(Long orderId);

    List<GoodsSalesDTO> selectTop10(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);
}
