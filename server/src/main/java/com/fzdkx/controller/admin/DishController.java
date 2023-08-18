package com.fzdkx.controller.admin;

import com.fzdkx.entity.Dish;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.DishService;
import com.fzdkx.vo.DishPageQueryVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/18 10:38
 */
@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {
    @Resource
    private DishService dishService;

    @ApiOperation("修改菜品")
    @PutMapping()
    public Result changeDish(){
        return Result.success();
    }

    @ApiOperation("移除菜品")
    @DeleteMapping()
    public Result removeDish(){
        return Result.success();
    }

    @ApiOperation("添加菜品")
    @PostMapping()
    public Result saveDish(){
        return Result.success();
    }

    @ApiOperation("根据ID查询菜品")
    @GetMapping("/{id}")
    public Result queryDishByID(@PathVariable("id") Long id){
        return Result.success();
    }
    @ApiOperation("根据分类ID查询菜品")
    @GetMapping("/list")
    public Result queryDishListByCategoryId(Long categoryId){
        return Result.success();
    }

    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public Result pageQueryDish(DishPageQueryVO dishPageQueryVO){
        PageResult<Dish> pageResult = dishService.pageQueryDish(dishPageQueryVO);
        return Result.success(pageResult);
    }

    @ApiOperation("修改菜品状态")
    @PostMapping("/status/{status}")
    public Result changStatus(@PathVariable("status") Integer status , Long id){
        return Result.success();
    }
}
