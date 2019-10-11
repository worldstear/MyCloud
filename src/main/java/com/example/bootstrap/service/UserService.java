package com.example.bootstrap.service;

import com.example.bootstrap.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    int registerUser(User user);

    void setUserTokenCookie(User loginUser, HttpServletRequest request, HttpServletResponse response);

    User findUserByUserId(String userId);
}
