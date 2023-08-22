package com.fzdkx.controller.admin;

import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:45
 */
@RestController("adminCategoryController")
@Slf4j
@Api(tags = "分类相关接口")
@RequestMapping("/admin/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @ApiOperation("分页查询分类获取菜品")
    @GetMapping("/page")
    public Result<PageResult<Category>> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult<Category> pageResult = categoryService.pageQueryCategory(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("修改分类或套餐状态")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable("status") Integer status, Long id) {
        categoryService.changeStatus(status, id);
        return Result.success();
    }

    @ApiOperation("移除分类或套餐")
    @DeleteMapping()
    public Result removeCategory(Integer id) {
        categoryService.removeCategory(id);
        return Result.success();
    }

    @ApiOperation("修改分类或套餐信息")
    @PutMapping()
    public Result changeCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.changeCategory(categoryDTO);
        return Result.success();
    }

    @ApiOperation("添加分类或套餐")
    @PostMapping()
    public Result saveCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }

    @ApiOperation("根据类型查询分类或套餐")
    @GetMapping("/list")
    public Result queryCategoryList(Integer type){
        List<Category> categoryList = categoryService.queryCategoryList(type);
        return Result.success(categoryList);
    }
}
