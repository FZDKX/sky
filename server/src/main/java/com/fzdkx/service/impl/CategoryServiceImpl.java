package com.fzdkx.service.impl;

import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import com.fzdkx.exception.SqlException;
import com.fzdkx.mapper.CategoryMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.CategoryService;
import com.fzdkx.utils.IdThreadLocal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.fzdkx.constant.MessageConstant.*;
import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:53
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageResult<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {

        // 设置分页信息
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        List<Category> categories;
        try {
            // 执行查询语句
            categories = categoryMapper.pageSelectCategory(categoryPageQueryDTO);
        } catch (Exception e) {
            throw new SqlException(SQL_CATEGORY_SELECT_ERROR);
        }
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
                .updateTime(LocalDateTime.now())
                .updateUser(IdThreadLocal.getId())
                .build();
        try {
            categoryMapper.updateCategory(category);
        } catch (Exception e) {
            throw new SqlException(SQL_CATEGORY_UPDATE_ERROR);
        }
    }

    @Override
    public void removeCategory(Integer id) {
        try {
            categoryMapper.deleteCategory(id);
        } catch (Exception e) {
            throw new RuntimeException(SQL_CATEGORY_DELETE_ERROR);
        }
    }

    @Override
    public void changeCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .updateTime(LocalDateTime.now())
                .updateUser(IdThreadLocal.getId()).build();
        BeanUtils.copyProperties(categoryDTO, category);
        try {
            categoryMapper.updateCategory(category);
        } catch (Exception e) {
            throw new SqlException(SQL_CATEGORY_UPDATE_ERROR);
        }
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        LocalDateTime now = LocalDateTime.now();
        Long id = IdThreadLocal.getId();
        Category category = Category.builder()
                .status(DEFAULT_STATUS)
                .createTime(now)
                .updateTime(now)
                .createUser(id)
                .updateUser(id)
                .build();
        BeanUtils.copyProperties(categoryDTO,category);
        try {
            categoryMapper.insertCategory(category);
        } catch (Exception e) {
            throw new SqlException(SQL_CATEGORY_INSERT_ERROR);
        }
    }
}
