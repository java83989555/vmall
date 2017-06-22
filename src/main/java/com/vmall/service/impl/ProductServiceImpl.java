package com.vmall.service.impl;

import com.github.pagehelper.PageInfo;
import com.vmall.common.ServerResponse;
import com.vmall.dao.ProductMapper;
import com.vmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午7:11
 */
@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    ProductMapper productMapper;


    @Override
    public ServerResponse<PageInfo> findProductList(Integer page, Integer size) {

        return null;
    }
}
