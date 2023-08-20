package com.fzdkx.controller.user;

import com.fzdkx.result.Result;
import com.fzdkx.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 发着呆看星
 * @create 2023/8/20 19:50
 */
@RestController("userShopController")
public class ShopController {
    @Resource
    private ShopService shopService;

    @GetMapping("/status")
    public Result queryStatus(){
        Integer status = shopService.getStatus();
        return Result.success();
    }
}
