package com.fzdkx.controller.user;

import com.fzdkx.result.Result;
import com.fzdkx.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/20 19:50
 */
@RestController("userShopController")
@Api(tags = "用户端店铺接口")
@RequestMapping("/admin/user")
public class ShopController {
    @Resource
    private ShopService shopService;

    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public Result queryStatus(){
        Integer status = shopService.getStatus();
        return Result.success();
    }
}
