package com.example.bootstrap.service;


import com.example.bootstrap.pojo.User;

public interface LoginService {

    User checkLogin(String userToken);
}
