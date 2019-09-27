package com.example.bootstrap.controller;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.UserService;
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
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String userLogin(User user, Model model, HttpServletResponse response, HttpServletRequest request){
        User loginUser = userMapper.selectUser(user);
        if(loginUser!=null){
            setUserTokenCookie(loginUser,request,response);
            return "redirect:/file/list";
        }else{
            return "/login";
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
           setUserTokenCookie(user,request,response);
           return "/index";
       }
       return "redirect:/register";
    }

    /**
     *将登录用户的状态保存到cookie中
     * @param loginUser
     * @param request
     * @param response
     */
    private void setUserTokenCookie(User loginUser,HttpServletRequest request,HttpServletResponse response){
        loginUser = userMapper.selectUser(loginUser);
        loginUser.setUserToken(UUID.randomUUID().toString());
        //System.out.println(loginUser);
        userMapper.updateUserToken(loginUser);
        //System.out.println(loginUser);
        Cookie cookie = new Cookie("user_token", loginUser.getUserToken());
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
        request.getSession().setAttribute("loginUser",loginUser);
    }
}
