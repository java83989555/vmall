package com.vmall.controller.backend;

import com.vmall.common.Const;
import com.vmall.common.ServerResponse;
import com.vmall.pojo.User;
import com.vmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by GV
 * DATE:2017/6/20
 * TIME:上午6:45
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(HttpSession session,String username,String password){
        ServerResponse<User> response=iUserService.login(username, password);
        if (response.isSuccess()){
            User user=response.getData();
            if (user.getRole()== Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员无法登陆");
            }
        }
        return response;
    }

}
