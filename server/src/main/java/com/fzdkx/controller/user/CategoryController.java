package com.fzdkx.controller.user;

import com.fzdkx.entity.Category;
import com.fzdkx.result.Result;
import com.fzdkx.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/21 21:05
 */
@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "分类与套餐相关接口")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> queryCategoryList(Integer type){
        List<Category> categoryList = categoryService.queryCategoryList(type);
        return Result.success(categoryList);
    }
}
