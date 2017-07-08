package com.vmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vmall.common.ResponseCode;
import com.vmall.common.ServerResponse;
import com.vmall.dao.CategoryMapper;
import com.vmall.pojo.Category;
import com.vmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午12:20
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;


    public static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public ServerResponse insertCategory(Integer parentId, String categoryName) {
        if (StringUtils.isBlank(categoryName) || parentId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServerResponse updateCategory(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("更新品类成功");
        }
        return ServerResponse.createByErrorMessage("更新品类信息失败");

    }

    @Override
    public ServerResponse<List<Category>> getChildrenCategory(Integer parentId) {
        if (parentId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到子级分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归获取当前分类和其子分类
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> getCatrgoryAndChildrenCategory(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        recursionGetCategorySet(categorySet, categoryId);
        List<Integer> idList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category category : categorySet) {
                idList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(idList);
    }

    //递归算法
    private Set<Category> recursionGetCategorySet(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            recursionGetCategorySet(categorySet, categoryItem.getId());
        }
        return categorySet;
    }

    public static void main(String[] args) {
        logger.info("info 数据");
        logger.debug("debug 数据");
        logger.error("error 数据");
    }

}
