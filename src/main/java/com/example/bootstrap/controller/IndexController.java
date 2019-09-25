package com.example.bootstrap.controller;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String indexPage(HttpServletRequest request,Model model){
        return "redirect:/file/list";
    }

    @GetMapping("/index")
    public String getIndexPage(){
        return "redirect:/file/list";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

}
