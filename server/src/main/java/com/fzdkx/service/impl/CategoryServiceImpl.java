package com.fzdkx.service.impl;

import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import com.fzdkx.exception.DeleteException;
import com.fzdkx.exception.ParamException;
import com.fzdkx.mapper.CategoryMapper;
import com.fzdkx.mapper.DishMapper;
import com.fzdkx.mapper.SetmealMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.fzdkx.constant.MessageConstant.*;
import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;
import static com.fzdkx.constant.SqlConstant.USER_LOCK;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:53
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private DishMapper dishMapper;

    @Resource
    private SetmealMapper setmealMapper;

    @Override
    public PageResult<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {

        // 设置分页信息
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        List<Category> categories;
        // 执行查询语句
        categories = categoryMapper.pageSelectCategory(categoryPageQueryDTO);
        // 对查询结果进行分页
        PageInfo<Category> categoryPageInfo = new PageInfo<>(categories);
        // 获取分页信息
        List<Category> list = categoryPageInfo.getList();
        long total = categoryPageInfo.getTotal();
        return new PageResult<>(total, list);
    }

    @Override
    public void changeStatus(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.updateCategory(category);
    }

    @Override
    public void removeCategory(Long id) {

        if (id == null){
            throw new ParamException(PARAM_IS_ERROR);
        }

        //查询分类关联菜品
        Integer count = dishMapper.selectDishByCategoryId(id);

        if (count != null && count > 0){
            throw new DeleteException(DELETE_IS_NO);
        }
        // 查询关联套餐
        count = setmealMapper.selectSetmealByCategoryId(id);

        if (count != null && count > 0){
            throw new DeleteException(DELETE_IS_NO);
        }

        // 删除分类
        categoryMapper.deleteCategory(id);
    }

    @Override
    public void changeCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder().build();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.updateCategory(category);
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .status(DEFAULT_STATUS)
                .build();
        BeanUtils.copyProperties(categoryDTO, category);
        categoryMapper.insertCategory(category);
    }

    @Override
    public List<Category> queryCategoryList(Integer type) {
        // 查询数据库
        List<Category> categoryList = categoryMapper.selectCategoryList(type);
        categoryList = categoryList.stream().filter(category -> {
            Long id = category.getId();
            Integer dish = dishMapper.selectByCategoryId(id);
            Integer setmeal = setmealMapper.selectByCategoryId(id);
            return (dish != null && dish > 0) || (setmeal != null && setmeal > 0);
        }).collect(Collectors.toList());
        return categoryList;
    }

    @Override
    public List<Category> queryCategoryListAll(Integer type) {
        return categoryMapper.selectCategoryList(type);
    }
}
