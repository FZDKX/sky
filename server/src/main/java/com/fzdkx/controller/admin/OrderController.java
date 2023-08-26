package com.fzdkx.controller.admin;

import com.fzdkx.dto.*;
import com.fzdkx.entity.Order;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.OrderService;
import com.fzdkx.vo.FindOrderVO;
import com.fzdkx.vo.OrderVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/26 15:22
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 订单搜索
     */
    @GetMapping("/conditionSearch")
    public Result<PageResult<FindOrderVO>> conditionSearch(FindOrderDTO findOrderDTO){
        PageResult<FindOrderVO> pageResult = orderService.conditionSearch(findOrderDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/details/{id}")
    public Result<OrderVO> details(@PathVariable("id") Long id){
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    /**
     * 派送订单
     */
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable("id") Long id){
        orderService.delivery(id);
        return Result.success();
    }

    /**
     * 接单
     */
    @PutMapping("/confirm")
    public Result confirm(@RequestBody ConfirmDTO confirmDTO){
        orderService.confirm(confirmDTO.getId());
        return Result.success();
    }

    /**
     * 拒单
     */
    @PutMapping("/rejection")
    public Result rejection(@RequestBody RejectionDTO rejectionDTO){
        orderService.rejection(rejectionDTO);
        return Result.success();
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel")
    public Result cancel(@RequestBody CancelDTO cancelDTO){
        orderService.adminCancel(cancelDTO);
        return Result.success();
    }

    /**
     * 完成订单
     */
    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable("id") Long id){
        orderService.complete(id);
        return Result.success();
    }

    /**
     * 各个状态的订单数量统计
     */
    @GetMapping("/statistics")
    public Result statistics(){
        StatisticsVO statisticsVO = orderService.statistics();
        return Result.success(statisticsVO);
    }
}
