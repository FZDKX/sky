package com.fzdkx.mapper;

import com.fzdkx.annotion.AutoFill;
import com.fzdkx.constant.AutoFillType;
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

    void deleteCategory(@Param("id") Long id);

    @AutoFill(AutoFillType.UPDATE)
    void updateCategory(Category category);

    @AutoFill(AutoFillType.INSERT)
    void insertCategory(Category category);

    List<Category> selectCategoryList(@Param("type") Integer type);

}
