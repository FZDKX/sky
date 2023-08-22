package com.fzdkx.controller.admin;

import com.fzdkx.dto.SetmealEditDTO;
import com.fzdkx.dto.SetmealPageQueryDTO;
import com.fzdkx.redis.RedisUtils;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.SetmealService;
import com.fzdkx.vo.SetmealPageQueryVO;
import com.fzdkx.vo.SetmealVO;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.fzdkx.constant.RedisConstant.SETMEAL_CATEGORY_PREFIX;

/**
 * @author 发着呆看星
 * @create 2023/8/20 11:36
 */
@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {
    @Resource
    private SetmealService setmealService;
    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult<SetmealPageQueryVO >> pageQuerySetmeal(SetmealPageQueryDTO setmealPageQueryDTO){
         PageResult<SetmealPageQueryVO > pageResult = setmealService.pageQuerySetmeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }



    @PostMapping()
    @ApiOperation("新增套餐")
    public Result saveSetmeal(@RequestBody SetmealVO setmealVO){
        redisUtils.deleteKey(SETMEAL_CATEGORY_PREFIX,setmealVO.getCategoryId().toString());
        setmealService.addSetmeal(setmealVO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据套餐ID查询套餐与对应菜品信息")
    public Result<SetmealVO> querySetmeal(@PathVariable("id") Long id){
        SetmealVO setmealVO = setmealService.findSetmeal(id);
        return Result.success(setmealVO);
    }

    @DeleteMapping()
    @ApiOperation("批量删除套餐")
    public Result removeSetmeal(@RequestParam("ids")List<Long> ids){
        redisUtils.deleteKey(SETMEAL_CATEGORY_PREFIX,null);
        setmealService.cancelSetmeal(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐状态")
    public Result changeSetmealStatus(@PathVariable("status") Integer status , @RequestParam("id") Long id){
        redisUtils.deleteKey(SETMEAL_CATEGORY_PREFIX,null);
        setmealService.alterSetmealStatus(status,id);
        return Result.success();
    }

    @PutMapping()
    public Result changeSetmeal(@RequestBody SetmealEditDTO setmealEditDTO){
        redisUtils.deleteKey(SETMEAL_CATEGORY_PREFIX,null);
        setmealService.alterSetmeal(setmealEditDTO);
        return Result.success();
    }
}
