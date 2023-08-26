package com.fzdkx.controller.user;

import com.fzdkx.dto.HistoryOrdersDTO;
import com.fzdkx.dto.OrderSubmitDTO;
import com.fzdkx.dto.OrdersPaymentDTO;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.OrderService;
import com.fzdkx.vo.OrderPaymentVO;
import com.fzdkx.vo.OrderSubmitVO;
import com.fzdkx.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/24 19:42
 */
@RestController("userOrderController")
@Api(tags = "订单接口")
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> orderSubmit(@RequestBody OrderSubmitDTO orderSubmitDTO){
        OrderSubmitVO orderSubmitVO = orderService.orderSubmit(orderSubmitDTO);
        return Result.success(orderSubmitVO);
    }
    /**
     * 订单支付
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);

        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable("id") Long id){
        orderService.reminder(id);
        return Result.success();
    }

    @GetMapping("/historyOrders")
    public Result<PageResult<OrderVO>> historyOrders(HistoryOrdersDTO historyOrdersDTO){
        PageResult<OrderVO> pageResult = orderService.historyOrders(historyOrdersDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/concel/{id}")
    public Result cancel(@PathVariable("id")Long id){
        orderService.cancel(id);
        return Result.success();
    }

    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> orderDetail(@PathVariable("id") Long id){
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    @PostMapping("/repetition/{id}")
    public Result repetition(@PathVariable("id") Long id){
        orderService.repetition(id);
        return Result.success();
    }
}

