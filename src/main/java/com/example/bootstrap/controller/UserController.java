package com.example.bootstrap.controller;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String userLogin(User user, Model model, HttpServletResponse response, HttpServletRequest request){
        User loginUser = userMapper.selectUser(user);
        if(loginUser!=null){
            userService.setUserTokenCookie(loginUser,request,response);
            return "redirect:file/list";
        }else{
            return "login";
        }
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/register")
    public String userRgister(User user,HttpServletResponse response,HttpServletRequest request){
       int registerResult =  userService.registerUser(user);
       if(registerResult>0){
           userService.setUserTokenCookie(user,request,response);
           return "index";
       }
       return "redirect:register";
    }

    @GetMapping("/user/find")
    public String findUserPage(){
        return "user/findUser";
    }

    @PostMapping("/user/find")
    @ResponseBody
    public String findUser(String user){
        User friend = userService.findUserByUserId(user);
        //给查找到的用户发送消息
        return "";
    }
}
