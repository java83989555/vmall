package com.vmall.controller.portal;

import com.vmall.common.Const;
import com.vmall.common.ResponseCode;
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
 * DATE:2017/6/18
 * TIME:下午4:14
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username, String password, HttpSession session) {

        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("退出成功");
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 检验用户名
     * 邮箱
     * 是否有效
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
       return iUserService.checkValid(str, type);
    }

    /**
     * 获取当前登陆用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> geUserInfo(HttpSession session){
       User user= (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆,请重新登陆");
        }
        return ServerResponse.createBySuccess(user);
    }

    /**
     * 获取用户的密码提示问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
       return iUserService.selectQuestion(username);
    }

    /**
     * 验证密码提示答案
     * 回答正确获得token
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
       return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 登陆页面
     * 通过密码提示问题重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){

       return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登陆成功 个人信息页面重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
       return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 个人信息管理页面 更新信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登陆");
        }

        //设置要更新的用户id和username为当前登陆的用户信息
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response=iUserService.updateUserInfo(user);

        if (response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 个人信息管理页面获得个人信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_information",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session){
        User currentUser= (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"用户未登陆");
        }
       return iUserService.getInformation(currentUser.getId());
    }
}
