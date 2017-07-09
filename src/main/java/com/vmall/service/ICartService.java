package com.vmall.service;

import com.vmall.common.ServerResponse;
import com.vmall.vo.CartVo;

/**
 * Created by GV
 * DATE:2017/6/24
 * TIME:上午10:38
 */
public interface ICartService {

    ServerResponse<CartVo> cartList(Integer userId);

    ServerResponse addCart(Integer userId, Integer productId,Integer count);

    ServerResponse updateCart(Integer userId, Integer productId, Integer count);

    ServerResponse deleteProduct(Integer userId, String productIds);

    ServerResponse selectedOrUnSelected(Integer userId, Integer productId, int checked);

    ServerResponse getCartProductCount(Integer userId);
}
