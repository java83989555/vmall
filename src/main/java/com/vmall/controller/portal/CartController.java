package com.vmall.controller.portal;

import com.vmall.common.Const;
import com.vmall.common.ResponseCode;
import com.vmall.common.ServerResponse;
import com.vmall.pojo.User;
import com.vmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by GV
 * DATE:2017/6/24
 * TIME:上午10:37
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ICartService iCartService;

    /**
     * @api {POST}  /cart/list    1获取购物车列表信息
     * @apiGroup  cart
     * @apiName list
     * @apiVersion 0.0.1
     * @apiDescription 获取购物车列表信息
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse list(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.cartList(user.getId());
    }


    /**
     * @api {POST}  /cart/add    2添加商品到购物车
     * @apiGroup  cart
     * @apiName add
     * @apiVersion 0.0.1
     * @apiDescription 2添加商品到购物车 有该商品则更新
     * @apiParam {Integer} productId 商品ID
     * @apiParam {Integer} count 添加数量
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(HttpSession session, Integer productId, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.addCart(user.getId(), productId, count);
    }
    /**
     * @api {POST}  /cart/update    3更新购物车内商品数量
     * @apiGroup  cart
     * @apiName update
     * @apiVersion 0.0.1
     * @apiDescription 3更新购物车内商品数量
     * @apiParam {Integer}  productId 商品ID
     * @apiParam {Integer}  count 商品数量
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(HttpSession session, Integer productId, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.updateCart(user.getId(), productId, count);
    }
    /**
     * @api {POST}  /cart/delete_product    4删除购物车内商品
     * @apiGroup  cart
     * @apiName delete_product
     * @apiVersion 0.0.1
     * @apiDescription 4删除购物车内指定商品
     * @apiParam {String}  productIds 选中商品ID多个商品以英文标点","分割
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "delete_product", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(HttpSession session, String productIds) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    /**
     * @api {POST}  /cart/select_all    5将购物车内商品全选
     * @apiGroup  cart
     * @apiName select_all
     * @apiVersion 0.0.1
     * @apiDescription 5将购物车内商品全选
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "select_all", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectedOrUnSelected(user.getId(), null, Const.Cart.CHECKET);
    }
    /**
     * @api {POST}  /cart/un_select_all    6取消对购物车内商品的全选
     * @apiGroup  cart
     * @apiName un_select_all
     * @apiVersion 0.0.1
     * @apiDescription 6取消对购物车内商品的全选
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "un_select_all", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectedOrUnSelected(user.getId(), null, Const.Cart.UN_CHECKET);
    }
    /**
     * @api {POST}  /cart/select    7单独选中指定商品
     * @apiGroup  cart
     * @apiName select
     * @apiVersion 0.0.1
     * @apiDescription 7单独选中指定商品
     * @apiParam {Integer}  productId  商品ID
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "select", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse select(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectedOrUnSelected(user.getId(), productId, Const.Cart.CHECKET);
    }
    /**
     * @api {POST}  /cart/un_select    8取消选中指定商品
     * @apiGroup  cart
     * @apiName un_select
     * @apiVersion 0.0.1
     * @apiDescription 8取消选中指定商品
     * @apiParam {Integer}  productId  商品ID
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "un_select", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse unSelect(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectedOrUnSelected(user.getId(), productId, Const.Cart.UN_CHECKET);
    }

    /**
     * @api {POST}  /cart/get_cart_product_count    9获取购物车内商品的总数
     * @apiGroup  cart
     * @apiName get_cart_product_count
     * @apiVersion 0.0.1
     * @apiDescription 9获取购物车内商品的总数
     * @apiSuccessExample {JSON} Success-Response:HTTP/1.1 200 OK
     * {
     * "state" : ,
     * "data" :
     *
     * "message":
     * }
     */
    @RequestMapping(value = "get_cart_product_count", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCartProductCount(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.getCartProductCount(user.getId());
    }
}

