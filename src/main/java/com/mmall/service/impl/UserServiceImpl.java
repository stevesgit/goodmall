package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }
        String md5password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5password);
        if (user == null) {
            return ServiceResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServiceResponse<String> register(User user) {
        ServiceResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getUsername(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_ADMIN);
        //md5
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServiceResponse.createByErrorMessage("注册失败");
        }
        return ServiceResponse.createBySuccess("注册成功");
    }

    @Override
    public ServiceResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
            if (Const.USERNAME.equals(type)) {
                int resCount = userMapper.checkUsername(str);
                if (resCount > 0) {
                    ServiceResponse.createByErrorMessage("用户名已经存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resCount = userMapper.checkEmail(str);
                if (resCount > 0) {
                    ServiceResponse.createByErrorMessage("邮箱已经存在");
                }
            }
        } else {
            return ServiceResponse.createByErrorMessage("参数错误");
        }
        return ServiceResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServiceResponse<String> selectQuestion(String username) {
        int resultCount = userMapper.checkUsername(username);
        ServiceResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServiceResponse.createByErrorMessage("用户名不存在");
        }
        String question = userMapper.selectQuetiosnByUsename(username);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(question)) {
            return ServiceResponse.createBySuccess(question);
        }
        return ServiceResponse.createByErrorMessage("没有找回密码问题");
    }

    public ServiceResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            //本地缓存
            TokenCache.setKey("token" + username, forgetToken);
            return ServiceResponse.createBySuccess(forgetToken);
        }
        return ServiceResponse.createByErrorMessage("问题的答案错误 lo");
    }

    @Override
    public ServiceResponse<String> forgetResetPassword(String username, String password, String forgetToken) {
        //校验
        if (org.apache.commons.lang3.StringUtils.isBlank(forgetToken)) {
            return ServiceResponse.createByErrorMessage("参数错误，token需要传递");
        }
        ServiceResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServiceResponse.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
            return ServiceResponse.createByErrorMessage("token无效或过期");
        }
        //更改
        if (org.apache.commons.lang3.StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(password);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServiceResponse.createByErrorMessage("修改密码成功");
            }
        } else {
            return ServiceResponse.createByErrorMessage("token错误，请重洗获取重置密码的token");
        }
        return ServiceResponse.createByErrorMessage("修改密码失败");
    }

    //    public static void main(String[] args) {
//        System.out.println(UUID.randomUUID().toString());
//    }
}
