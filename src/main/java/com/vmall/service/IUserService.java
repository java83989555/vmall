package com.vmall.service;

import com.vmall.common.ServerResponse;
import com.vmall.pojo.Product;
import com.vmall.pojo.User;

import java.util.List;

/**
 * Created by GV
 * DATE:2017/6/18
 * TIME:下午7:43
 */
public interface IUserService {
    ServerResponse<User> login(String username,String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateUserInfo(User user);

    ServerResponse<User> getInformation(Integer id);

    ServerResponse checkAdminRole(User user);


}
