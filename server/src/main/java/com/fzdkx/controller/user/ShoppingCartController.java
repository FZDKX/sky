package com.fzdkx.controller.user;

import com.fzdkx.dto.CartDTO;
import com.fzdkx.entity.ShoppingCart;
import com.fzdkx.result.Result;
import com.fzdkx.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/23 9:39
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车接口")
@Slf4j
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result saveCart(@RequestBody CartDTO cartDTO){
        shoppingCartService.addCart(cartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询当前用户购物车集合")
    public Result<List<ShoppingCart>> queryCartList(){
        List<ShoppingCart> list = shoppingCartService.queryCartList();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result cleanCartAll(){
        shoppingCartService.cleanCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("根据购物车ID减少购物车")
    public Result cleanCartOne(@RequestBody CartDTO cartDTO){
        shoppingCartService.cleanCartOne(cartDTO);
        return Result.success();
    }
}
