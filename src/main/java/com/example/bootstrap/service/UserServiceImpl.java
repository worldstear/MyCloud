package com.example.bootstrap.service;

import com.example.bootstrap.mapper.UserMapper;
import com.example.bootstrap.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int registerUser(User user) {
        int result = userMapper.insertUser(user);
        return result;
    }
}
