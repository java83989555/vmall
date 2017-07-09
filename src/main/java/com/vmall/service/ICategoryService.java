package com.vmall.service;

import com.vmall.common.ServerResponse;
import com.vmall.pojo.Category;

import java.util.List;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午12:19
 */
public interface ICategoryService {

    ServerResponse insertCategory(Integer parentId, String categoryName);

    ServerResponse updateCategory(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenCategory(Integer categoryId);

    ServerResponse<List<Integer>> getCatrgoryAndChildrenCategory(Integer categoryId);
}
