package com.fzdkx.controller.admin;

import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import com.fzdkx.entity.Employee;
import com.fzdkx.result.PageResult;
import com.fzdkx.result.Result;
import com.fzdkx.service.CategoryService;
import com.fzdkx.utils.IdThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:45
 */
@RestController
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result<PageResult<Category>> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult<Category> pageResult = categoryService.pageQueryCategory(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable("status") Integer status, Long id) {
        categoryService.changeStatus(status, id);
        return Result.success();
    }

    @DeleteMapping()
    public Result removeCategory(Integer id) {
        categoryService.removeCategory(id);
        return Result.success();
    }

    @PutMapping()
    public Result changeCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.changeCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping()
    public Result saveCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result queryCategoryList(Integer type){
        List<Category> categoryList = categoryService.queryCategoryList(type);
        return Result.success(categoryList);
    }
}
