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

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:45
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/category/page")
    public Result<PageResult<Category>> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult<Category> pageResult = categoryService.pageQueryCategory(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/category/status/{status}")
    public Result changeStatus(@PathVariable("status") Integer status, Long id) {
        categoryService.changeStatus(status, id);
        return Result.success();
    }

    @DeleteMapping("/category")
    public Result removeCategory(Integer id) {
        categoryService.removeCategory(id);
        return Result.success();
    }

    @PutMapping("/category")
    public Result changeCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.changeCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping("/category")
    public Result saveCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.saveCategory(categoryDTO);
        return Result.success();
    }
}
