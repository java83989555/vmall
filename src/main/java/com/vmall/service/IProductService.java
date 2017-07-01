package com.vmall.service;

import com.github.pagehelper.PageInfo;
import com.vmall.common.ServerResponse;
import com.vmall.pojo.Product;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午7:11
 */

public interface IProductService {

    ServerResponse<PageInfo> findProductList(Integer page, Integer size);

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse getProductDetail(Integer productId);

    ServerResponse setProductStatus(Integer productId, Integer status);

    ServerResponse findByProductNameAndProductId(String productName, Integer productId, Integer page, Integer size);

    ServerResponse getPortalProductDetail(Integer productId);

    ServerResponse findByKeyWordAndCategoryId(String keyWord, Integer categoryId, int page, int size, String orderBy);
}
