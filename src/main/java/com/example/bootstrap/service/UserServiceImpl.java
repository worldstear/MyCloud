package com.example.bootstrap.service;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int registerUser(User user) {
        int result = userMapper.insertUser(user);
        return result;
    }

    @Override
    /**
     *将登录用户的状态保存到cookie中
     * @param loginUser
     * @param request
     * @param response
     */
    public void setUserTokenCookie(User loginUser,HttpServletRequest request,HttpServletResponse response){
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

    @Override
    public User findUserByUserId(String userId) {
        return userMapper.selectUserByUserId(userId);
    }
}
