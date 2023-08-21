package com.fzdkx.controller.admin;

import com.fzdkx.result.Result;
import com.fzdkx.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/20 19:29
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

    @Resource
    private ShopService shopService;

    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public Result queryStatus(){
        Integer status = shopService.getStatus();
        return Result.success(status);
    }

    @PutMapping("/{status}")
    @ApiOperation("修改店铺状态")
    public Result changeStatus(@PathVariable Integer status){
        shopService.editStatus(status);
        return Result.success();
    }
}
