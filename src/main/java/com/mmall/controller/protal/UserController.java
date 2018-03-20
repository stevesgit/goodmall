package com.mmall.controller.protal;

import com.mmall.common.Const;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
@ResponseBody
public class UserController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session) {
        ServiceResponse<User> response = iUserService.login(username, password);
        return ServiceResponse.createByErrorMessage("用户未登录，无法获取用户信息");

//        if (response.isSuccess()) {
//            session.setAttribute(Const.CURRENT_USER, response.getData());
//        }
//
//        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> registier(User user) {
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServiceResponse.createBySuccess(user);
        }
        return ServiceResponse.createByErrorMessage("用户未登录，无法获取用户信息");
    }

    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> forgetResetPassword(String username, String password, String forgetToken) {
        return iUserService.forgetResetPassword(username, password, forgetToken);
    }
}
