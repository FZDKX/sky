package com.fzdkx.controller.user;

import com.fzdkx.entity.Setmeal;
import com.fzdkx.mapper.SetmealMapper;
import com.fzdkx.result.Result;
import com.fzdkx.service.DishService;
import com.fzdkx.service.SetmealService;
import com.fzdkx.vo.DishItemVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/21 21:31
 */
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private DishService dishService;

    @GetMapping("/list")
    public Result<List<Setmeal>> querySetmealList(Long categoryId){
        List<Setmeal> setmealList = setmealService.querySetmealListByCategory(categoryId);
        return Result.success(setmealList);
    }

    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> querySetmeal(@PathVariable("id") Long id){
        List<DishItemVO> dishItemVOList = dishService.queryDishItemList(id);
        return Result.success(dishItemVOList);
    }
}
