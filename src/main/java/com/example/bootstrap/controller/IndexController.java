package com.example.bootstrap.controller;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String indexPage(HttpServletRequest request,Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            return "/login";
        }

        String userToken = "";
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("user_token")){
                userToken = cookie.getValue();
            }
        }

        User loginUser = loginService.checkLogin(userToken);
        if(loginUser!=null){
            model.addAttribute("loginUser",loginUser);
            return "index";
        }
        return "login";
    }

    @GetMapping("/index")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

}
