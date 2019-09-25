package com.example.bootstrap.intercepter;

import com.example.bootstrap.pojo.User;
import com.example.bootstrap.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //进行用户登录判断
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0){
            response.sendRedirect("/login");
            return false;
        }

        String userToken = "";
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("user_token")){
                userToken = cookie.getValue();
            }
        }

        User loginUser = loginService.checkLogin(userToken);
        if(loginUser==null){
            response.sendRedirect("/login");
            return false;
        }
        //model.addAttribute("loginUser",loginUser);
        request.getSession().setAttribute("loginUser",loginUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
