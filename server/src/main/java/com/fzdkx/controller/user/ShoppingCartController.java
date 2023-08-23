package com.fzdkx.controller.user;

import com.fzdkx.dto.CartDTO;
import com.fzdkx.entity.ShoppingCart;
import com.fzdkx.result.Result;
import com.fzdkx.service.ShoppingCartService;
import io.swagger.annotations.Api;
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
    public Result saveCart(@RequestBody CartDTO cartDTO){
        shoppingCartService.addCart(cartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> queryCartList(){
        List<ShoppingCart> list = shoppingCartService.queryCartList();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    public Result cleanCartAll(){
        shoppingCartService.cleanCart();
        return Result.success();
    }

    @PostMapping("/sub")
    public Result cleanCartOne(@RequestBody CartDTO cartDTO){
        shoppingCartService.cleanCartOne(cartDTO);
        return Result.success();
    }
}
