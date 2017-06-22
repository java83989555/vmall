package com.vmall.controller.backend;

import com.vmall.common.Const;
import com.vmall.common.ResponseCode;
import com.vmall.common.ServerResponse;
import com.vmall.pojo.Category;
import com.vmall.pojo.User;
import com.vmall.service.ICategoryService;
import com.vmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:上午11:14
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    ICategoryService iCategoryService;

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "add_category", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, Integer parentId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(), "未登陆");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.insertCategory(parentId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无操作权限");
        }

    }


    @RequestMapping(value = "set_category_name", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(), "未登陆");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.updateCategory(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无操作权限");
        }

    }

    @RequestMapping(value = "get_category", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(defaultValue = "0", value = "categoryId") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(), "未登陆");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //获取平级子分类不递归
            return iCategoryService.getChildrenCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无操作权限");
        }
    }

    @RequestMapping(value = "get_deep_category", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(defaultValue = "0", value = "categoryId")Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(), "未登陆");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //获取给定品类 下属所有分类ID 递归获取
//            0->10000->100000
            return iCategoryService.getCatrgoryAndChildrenCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("不是管理员，无操作权限");
        }
    }
}
