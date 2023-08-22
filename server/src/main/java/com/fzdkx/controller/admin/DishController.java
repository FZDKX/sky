package com.fzdkx.controller.admin;

import com.fzdkx.redis.RedisUtils;
import com.fzdkx.vo.DishAndFlavorVO;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.DishService;
import com.fzdkx.dto.DishChangeDTO;
import com.fzdkx.vo.DishPageQueryVO;
import com.fzdkx.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.fzdkx.constant.RedisConstant.DISH_CATEGORY_PREFIX;

/**
 * @author 发着呆看星
 * @create 2023/8/18 10:38
 */
@RestController("adminDishController")
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {
    @Resource
    private DishService dishService;
    @Resource
    private RedisUtils redisUtils;

    @ApiOperation("修改菜品")
    @PutMapping()
    public Result changeDish(@RequestBody DishChangeDTO dishChangeDTO) {
        redisUtils.deleteKey(DISH_CATEGORY_PREFIX,null);
        dishService.changeDish(dishChangeDTO);
        return Result.success();
    }

    @ApiOperation("移除菜品")
    @DeleteMapping()
    public Result removeDish(@RequestParam("ids") List<Long> ids) {
        redisUtils.deleteKey(DISH_CATEGORY_PREFIX,null);
        dishService.remove(ids);
        return Result.success();
    }

    @ApiOperation("添加菜品")
    @PostMapping()
    public Result saveDish(@RequestBody DishChangeDTO dishChangeDTO) {
        redisUtils.deleteKey(DISH_CATEGORY_PREFIX,dishChangeDTO.getCategoryId().toString());
        dishService.saveDish(dishChangeDTO);
        return Result.success();
    }

    @ApiOperation("根据ID查询菜品")
    @GetMapping("/{id}")
    public Result<DishAndFlavorVO> queryDishByID(@PathVariable("id") Long id) {
        DishAndFlavorVO dishAndFlavorVO = dishService.queryDishAnFlavorById(id);
        return Result.success(dishAndFlavorVO);
    }

    @ApiOperation("根据分类ID查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> queryDishListByCategoryId(Long categoryId) {
        List<DishVO> dishList = dishService.queryDishListByCategoryId(categoryId);
        return Result.success(dishList);
    }

    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public Result<PageResult<DishVO>> pageQueryDish(DishPageQueryVO dishPageQueryVO) {
        PageResult<DishVO> pageResult = dishService.pageQueryDish(dishPageQueryVO);
        return Result.success(pageResult);
    }

    @ApiOperation("修改菜品状态")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable("status") Integer status, Long id) {
        redisUtils.deleteKey(DISH_CATEGORY_PREFIX,null);
        dishService.changeDishStatus(status, id);
        return Result.success();
    }
}
