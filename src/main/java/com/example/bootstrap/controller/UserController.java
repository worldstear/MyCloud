package com.example.bootstrap.controller;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public String userLogin(User user, Model model, HttpServletResponse response, HttpServletRequest request){
        User loginUser = userMapper.selectUser(user);
        if(loginUser!=null){
            loginUser.setUserToken(UUID.randomUUID().toString());
            userMapper.updateUserToken(loginUser);
            System.out.println(loginUser);
            Cookie cookie = new Cookie("user_token", loginUser.getUserToken());
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            request.getSession().setAttribute("loginUser",loginUser);
            return "index";
        }else{
            return "/";
        }
    }

}
