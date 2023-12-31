package com.fzdkx.service.impl;

import com.fzdkx.entity.Order;
import com.fzdkx.mapper.DishMapper;
import com.fzdkx.mapper.OrderMapper;
import com.fzdkx.mapper.SetmealMapper;
import com.fzdkx.mapper.UserMapper;
import com.fzdkx.service.WorkSpaceService;
import com.fzdkx.vo.BusinessDataVO;
import com.fzdkx.vo.OverviewDishesVO;
import com.fzdkx.vo.OverviewOrdersVO;
import com.fzdkx.vo.OverviewSetmealsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;
import static com.fzdkx.constant.SqlConstant.LOCK_STATUS;
import static com.fzdkx.entity.Order.*;

/**
 * @author 发着呆看星
 * @create 2023/8/28 14:26
 */
@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private DishMapper dishMapper;

    @Override
    public BusinessDataVO businessData(LocalDateTime begin ,LocalDateTime end) {
        // 获取当日新增用户数
        Integer newUser = userMapper.selectNewUser(begin, end);
        // 获取当日有效订单数
        Integer valid = orderMapper.ordersStatistics(begin, end, Order.SUCCESS);
        // 获取当日总订单数
        Integer total = orderMapper.ordersStatistics(begin, end, null);

        double orderCompletionRate = 0.0;
        // 如果总订单不为0
        if (!total.equals(0)) {
            // 获取当日订单完成率
            orderCompletionRate = valid.doubleValue() / total;
        }

        BigDecimal turnover = new BigDecimal("0.00");
        BigDecimal avgPrice = new BigDecimal("0.00");

        // 如果有效订单数不为0
        if (!valid.equals(0)) {
            // 获取当日营业额
            turnover = orderMapper.selectTurnover(begin, end);

            // 获取当日平均客单价
            avgPrice = turnover.divide(new BigDecimal(valid), 2, RoundingMode.HALF_UP);

        }
        return new BusinessDataVO(newUser, orderCompletionRate, turnover, avgPrice, valid);
    }

    @Override
    public OverviewSetmealsVO overviewSetmeals() {
        Integer unlock = setmealMapper.selectByStatus(DEFAULT_STATUS);
        Integer lock = setmealMapper.selectByStatus(LOCK_STATUS);
        return new OverviewSetmealsVO(lock, unlock);
    }

    @Override
    public OverviewDishesVO overviewDishes() {
        Integer unlock = dishMapper.selectByStatus(DEFAULT_STATUS);
        Integer lock = dishMapper.selectByStatus(LOCK_STATUS);
        return new OverviewDishesVO(lock, unlock);
    }

    @Override
    public OverviewOrdersVO overviewOrders() {
        // 查询全部订单
        Integer allOrders = orderMapper.selectByStatus(null);
        // 查询已取消订单
        Integer cancelledOrders = orderMapper.selectByStatus(CANCEL);
        // 查询已完成订单
        Integer completedOrders = orderMapper.selectByStatus(SUCCESS);
        // 查询带派送订单
        Integer deliveredOrders = orderMapper.selectByStatus(RECEIVE_ORDER);
        // 查询待接单订单
        Integer waitingOrders = orderMapper.selectByStatus(WAIT_ORDER);
        return new OverviewOrdersVO(allOrders, cancelledOrders, completedOrders, deliveredOrders, waitingOrders);
    }
}
