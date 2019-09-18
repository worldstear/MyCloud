package com.example.bootstrap.mapper;

import com.example.bootstrap.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select id,username,password,user_token from t_user where username = #{username} and password = #{password}")
    User selectUser(User user);

    @Update("update t_user set user_token = #{userToken} where id = #{id}")
    void updateUserToken(User loginUser);

    @Select("select id,username,password,user_token from t_user where user_token = #{userToken}")
    User selectUserToken(String userToken);
}
