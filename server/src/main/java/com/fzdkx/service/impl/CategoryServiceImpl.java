package com.fzdkx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fzdkx.dto.CategoryDTO;
import com.fzdkx.dto.CategoryPageQueryDTO;
import com.fzdkx.entity.Category;
import com.fzdkx.mapper.CategoryMapper;
import com.fzdkx.result.PageResult;
import com.fzdkx.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.fzdkx.constant.RedisConstant.CATEGORY_KEY;
import static com.fzdkx.constant.SqlConstant.DEFAULT_STATUS;

/**
 * @author 发着呆看星
 * @create 2023/8/16 17:53
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private StringRedisTemplate template;
    @Resource
    private CategoryMapper categoryMapper;

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
    public void removeCategory(Integer id) {
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
        // 先查询缓存，看是否有数据
        String json = template.opsForValue().get(CATEGORY_KEY);

        // 如果缓存有数据，则直接返回
        if (json != null && !json.equals("")) {
            return JSONObject.parseArray(json, Category.class);
        }

        // 查询数据库
        List<Category> categoryList = categoryMapper.selectCategoryList(type);

        // list 转 json
        json= JSONObject.toJSONString(categoryList);

        // 重建缓存
        template.opsForValue().set(CATEGORY_KEY,json);

        // 返回
        return categoryList;
    }
}
