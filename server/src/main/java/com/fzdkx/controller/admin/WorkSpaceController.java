package com.fzdkx.controller.admin;

import com.fzdkx.result.Result;
import com.fzdkx.service.WorkSpaceService;
import com.fzdkx.vo.BusinessDataVO;
import com.fzdkx.vo.OverviewDishesVO;
import com.fzdkx.vo.OverviewOrdersVO;
import com.fzdkx.vo.OverviewSetmealsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/28 14:18
 */
@RestController
@RequestMapping("/admin/workspace")
public class WorkSpaceController {

    @Resource
    private WorkSpaceService workSpaceService;

    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData(){
        BusinessDataVO businessDataVO = workSpaceService.businessData();
        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewSetmeals")
    public Result<OverviewSetmealsVO> overviewSetmeals(){
        OverviewSetmealsVO overviewSetmealsVO = workSpaceService.overviewSetmeals();
        return Result.success(overviewSetmealsVO);
    }

    @GetMapping("/overviewDishes")
    public Result<OverviewDishesVO> overviewDishes(){
        OverviewDishesVO overviewDishesVO = workSpaceService.overviewDishes();
        return Result.success(overviewDishesVO);
    }

    @GetMapping("/overviewOrders")
    public Result<OverviewOrdersVO> overviewOrders(){
        OverviewOrdersVO overviewOrdersVO = workSpaceService.overviewOrders();
        return Result.success(overviewOrdersVO);
    }

}
