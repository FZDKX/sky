package com.fzdkx.controller.user;

import com.fzdkx.result.Result;
import com.fzdkx.service.DishService;
import com.fzdkx.vo.DishAndFlavorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.fzdkx.constant.RedisConstant.DISH_CATEGORY_PREFIX;

/**
 * @author 发着呆看星
 * @create 2023/8/21 21:09
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "菜品相关接口")
public class DishController {

    @Resource
    private DishService dishService;

    @GetMapping("/list")
    @ApiOperation("根据分类ID查询菜品信息")
    public Result<List<DishAndFlavorVO>> queryDishList(Long categoryId){
        List<DishAndFlavorVO> dishAndFlavorVOList = dishService.queryDishAnFlavorByCategoryId(categoryId);
        return Result.success(dishAndFlavorVOList);
    }
}
