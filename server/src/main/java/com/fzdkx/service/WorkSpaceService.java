package com.fzdkx.service;

import com.fzdkx.vo.BusinessDataVO;
import com.fzdkx.vo.OverviewDishesVO;
import com.fzdkx.vo.OverviewOrdersVO;
import com.fzdkx.vo.OverviewSetmealsVO;

import java.time.LocalDateTime;

/**
 * @author 发着呆看星
 * @create 2023/8/28 14:26
 */
public interface WorkSpaceService {
    BusinessDataVO businessData(LocalDateTime begin , LocalDateTime end);

    OverviewSetmealsVO overviewSetmeals();

    OverviewDishesVO overviewDishes();


    OverviewOrdersVO overviewOrders();

}
