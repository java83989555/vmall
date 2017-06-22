package com.vmall.service;

import com.github.pagehelper.PageInfo;
import com.vmall.common.ServerResponse;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:下午7:11
 */

public interface IProductService {
    ServerResponse<PageInfo> findProductList(Integer page, Integer size);


}
