package com.fzdkx.task;

import com.fzdkx.entity.Order;
import com.fzdkx.mapper.OrderDetailMapper;
import com.fzdkx.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/26 9:03
 */
@Slf4j
@Component
public class OrderTask {

    @Resource
    private OrderMapper orderMapper;

    // 每隔一分钟，查看订单超时未支付
    @Scheduled(cron = "0 * * * * ?")
    public void paymentTimout() {
        LocalDateTime now = LocalDateTime.now();
        log.info("正在进行订单支付超时处理工作：{}", now);

        // 查看是否有订单超时
        List<Long> orderIds = orderMapper.selectPayTimout(now.plusMinutes(-15));

        if (orderIds != null && orderIds.size() > 0) {
            // 如果有则取消订单
            for (Long orderId : orderIds) {
                Order order = Order.builder()
                        .id(orderId)
                        .status(6)
                        .cancelReason("订单超时")
                        .checkoutTime(now).build();
                orderMapper.update(order);
            }
        }

    }


    // 每天打烊后一小时，查看是否有订单孩子配送
    @Scheduled(cron = "0 0 1 * * ?")
    public void completeDelivery() {
        LocalDateTime now = LocalDateTime.now();
        log.info("对昨天的仍在配送的订单状态进行修改：{}", now);
        // 查看是否有昨天的订单还在配送
        List<Long> ids = orderMapper.selectDeliveryOrder(now.plusHours(-1));

        // 如果有，就对订单状态进行修改
        if (ids != null && ids.size() > 0) {
            for (Long id : ids) {
                Order order = Order.builder()
                                    .id(id)
                                    .status(5).build();
                orderMapper.update(order);
            }
        }
    }
}
