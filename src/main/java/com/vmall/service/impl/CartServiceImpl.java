package com.vmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.vmall.common.BigDecimalUtil;
import com.vmall.common.Const;
import com.vmall.common.ResponseCode;
import com.vmall.common.ServerResponse;
import com.vmall.dao.CartMapper;
import com.vmall.dao.ProductMapper;
import com.vmall.pojo.Cart;
import com.vmall.pojo.Product;
import com.vmall.service.ICartService;
import com.vmall.util.PropertiesUtil;
import com.vmall.vo.CartProductVo;
import com.vmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by GV
 * DATE:2017/6/24
 * TIME:上午10:38
 */

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> cartList(Integer userId) {
        return ServerResponse.createBySuccess(this.getCartVoLimit(userId));
    }

    @Override
    public ServerResponse addCart(Integer userId, Integer productId, Integer count) {
        if (userId == null || productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        //没有新增 有则更新
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setChecked(Const.Cart.CHECKET);
            newCart.setQuantity(count);
            cartMapper.insert(newCart);
        } else {
            cart.setQuantity(cart.getQuantity() + count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return ServerResponse.createBySuccess(this.getCartVoLimit(userId));
    }

    @Override
    public ServerResponse updateCart(Integer userId, Integer productId, Integer count) {
        if (userId == null || productId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.cartList(userId);
    }

    @Override
    public ServerResponse deleteProduct(Integer userId, String productIds) {
        if (userId == null || productIds == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<String> list = Splitter.on(",").splitToList(productIds);
        cartMapper.deleteByUserIdAndProductIds(userId, list);
        return this.cartList(userId);
    }

    @Override
    public ServerResponse selectedOrUnSelected(Integer userId, Integer productId, int checked) {
        cartMapper.updateChecked(userId,productId,checked);
        return this.cartList(userId);
    }

    @Override
    public ServerResponse getCartProductCount(Integer userId) {
        int count=cartMapper.getCartProductCount(userId);
        return ServerResponse.createBySuccess(count);
    }

    /**
     * 高复用获取购物车整体信息
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                CartProductVo cartProductVo = new CartProductVo();
                if (product != null) {
                    cartProductVo.setId(product.getId());
                    cartProductVo.setUserId(userId);
                    cartProductVo.setProductId(product.getId());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductChecked(cartItem.getChecked());
                    //判断库存
                    if (cartItem.getQuantity() <= product.getStock()) {
                        cartProductVo.setQuantity(cartItem.getQuantity());
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_QUANTITY_SUCCESS);
                    } else {
                        //如果购物车内商品数量大于库存数量
                        cartProductVo.setQuantity(product.getStock());
                        //此字段仅作用于通知前端
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_QUANTITY_FAIL);
                        //对持久化的购物车内商品数量进行矫正更新,将购物车内商品数量更新为最大商品库存数
                        Cart updateCart = new Cart();
                        updateCart.setId(cartItem.getId());
                        updateCart.setQuantity(product.getStock());
                        cartMapper.updateByPrimaryKeySelective(updateCart);
                    }
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(cartItem.getQuantity().doubleValue(), product.getPrice().doubleValue()));
                    //如果已经勾选 则 累加到购物车总价中去

                }
                if (cartItem.getChecked() == Const.Cart.CHECKET) {
                    cartTotalPrice = BigDecimalUtil.add(cartProductVo.getProductTotalPrice().doubleValue(), cartTotalPrice.doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;

    }

    //判断购物车内的商品是否全选
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
