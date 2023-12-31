package com.fzdkx.service;

import com.fzdkx.dto.*;
import com.fzdkx.result.PageResult;
import com.fzdkx.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @author 发着呆看星
 * @create 2023/8/24 19:55
 */
public interface OrderService {
    OrderSubmitVO orderSubmit(OrderSubmitDTO orderSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    void reminder(Long id);

    PageResult<OrderVO> historyOrders(HistoryOrdersDTO historyOrdersDTO);

    void cancel(Long id);

    OrderVO orderDetail(Long id);

    void repetition(Long id);

    PageResult<FindOrderVO> conditionSearch(FindOrderDTO findOrderDTO);

    void delivery(Long id);

    void confirm(Long id);

    void rejection(RejectionDTO rejectionDTO);

    void adminCancel(CancelDTO cancelDTO);

    void complete(Long id);

    StatisticsVO statistics();

    TurnoverVO turnoverStatistics(LocalDate begin, LocalDate end);

    OrdersStatisticsVO ordersStatistics(LocalDate begin, LocalDate end);

    void export(HttpServletResponse response);

}
