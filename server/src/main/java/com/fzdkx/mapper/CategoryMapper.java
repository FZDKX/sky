package com.fzdkx.mapper;

import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/16 18:00
 */
public interface CategoryMapper {

    List<Category> pageSelectCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteCategory(@Param("id") Integer id);

    void updateCategory(Category category);

    void insertCategory(Category category);
}
