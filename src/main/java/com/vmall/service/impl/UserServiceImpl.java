package com.vmall.service.impl;

import com.vmall.common.Const;
import com.vmall.common.ServerResponse;
import com.vmall.common.TokenCache;
import com.vmall.dao.UserMapper;
import com.vmall.pojo.Product;
import com.vmall.pojo.User;
import com.vmall.service.IUserService;
import com.vmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by GV
 * DATE:2017/6/18
 * TIME:下午7:44
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        Integer i = userMapper.checkUsername(username);
        if (i == 0) {
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    @Override
    public ServerResponse<String> register(User user) {
        //依此验证 用户名 和 邮箱
        ServerResponse<String> validResponse = checkValid(user.getUsername(), Const.USER_NAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int i = userMapper.insert(user);
        if (i == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            if (Const.USER_NAME.equals(type)) {
                Integer integer = userMapper.checkUsername(str);
                if (integer > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                Integer integer = userMapper.checkEmail(str);
                if (integer > 0) {
                    return ServerResponse.createByErrorMessage("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        //未找到 则成功
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse<String> serverResponse = checkValid(username, Const.USER_NAME);
        if (serverResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.findQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("用户密码提示问题为空");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        Integer resultCount = userMapper.checkAnswer(username, question, answer);
        //说明用的问题和答案是正确的
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            //存入缓存
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("提示问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，需要传递token");
        }
        ServerResponse<String> serverResponse = checkValid(username, Const.USER_NAME);
        if (serverResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token过期或无效");
        }
        if (StringUtils.equals(forgetToken, token)) {
            int i = userMapper.updatePasswordByUsername(username, MD5Util.MD5EncodeUtf8(passwordNew));
            if (i == 0) {
                return ServerResponse.createByErrorMessage("更新失败");
            }
        } else {
            return ServerResponse.createByErrorMessage("token有误");
        }
        return ServerResponse.createBySuccessMessage("重置密码成功");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.
        //因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int i=userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if (i==0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        i=userMapper.updateByPrimaryKeySelective(user);
        if (i>0){
            return ServerResponse.createBySuccessMessage("更新密码成功");
        }
        return ServerResponse.createByErrorMessage("更新密码失败");
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user) {
        int rowCount=userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (rowCount>0){
            return ServerResponse.createByErrorMessage("邮箱地址已存，请重新设置");
        }
        //只更新部分信息 必须设置ID
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int i=userMapper.updateByPrimaryKeySelective(updateUser);
        if (i>0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user=userMapper.selectByPrimaryKey(id);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        if (user==null||user.getRole().intValue()!=Const.Role.ROLE_ADMIN){
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();

    }



    public static void main(String[] args) {
        System.out.println(MD5Util.MD5EncodeUtf8("admin"));
    }

}
