package com.example.bootstrap.service;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkLogin(String userToken) {
        return userMapper.selectUserToken(userToken);
    }
}
