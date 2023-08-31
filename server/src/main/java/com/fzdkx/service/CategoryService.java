package com.fzdkx.service;

import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import com.fzdkx.result.PageResult;

import java.util.List;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:53
 */
public interface CategoryService {
    /**
     * 分页查询分类信息
     */
    PageResult<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改分类状态信息
     */
    void changeStatus(Integer status, Long id);

    /**
     * 根据ID删除分类
     */
    void removeCategory(Long id);

    /**
     * 修改分类信息
     */
    void changeCategory(CategoryDTO categoryDTO);

    /**
     * 新增分类或套餐
     */
    void saveCategory(CategoryDTO categoryDTO);

    /**
     * 根据类型查询分类集合
     */
    List<Category> queryCategoryList(Integer type);

    List<Category> queryCategoryListAll(Integer type);
}
