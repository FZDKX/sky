package com.fzdkx.controller.admin;

import com.fzdkx.entity.OrderDetail;
import com.fzdkx.result.Result;
import com.fzdkx.service.OrderDetailService;
import com.fzdkx.service.OrderService;
import com.fzdkx.service.UserService;
import com.fzdkx.vo.OrdersStatisticsVO;
import com.fzdkx.vo.Top10VO;
import com.fzdkx.vo.TurnoverVO;
import com.fzdkx.vo.UserStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author 发着呆看星
 * @create 2023/8/28 9:41
 */
@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计相关接口")
public class ReportController {

    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;
    @Resource
    private OrderDetailService orderDetailService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        TurnoverVO turnoverVO = orderService.turnoverStatistics(begin,end);
        return Result.success(turnoverVO);
    }

    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserStatisticsVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        UserStatisticsVO userStatisticsVO = userService.userStatistics(begin,end);
        return Result.success(userStatisticsVO);
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation("用户统计")
    public Result<OrdersStatisticsVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        OrdersStatisticsVO ordersStatisticsVO = orderService.ordersStatistics(begin,end);
        return Result.success(ordersStatisticsVO);
    }

    @GetMapping("/top10")
    @ApiOperation("销量排名")
    public Result<Top10VO>top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        Top10VO top10VO = orderDetailService.top10(begin,end);
        return Result.success(top10VO);
    }

}
