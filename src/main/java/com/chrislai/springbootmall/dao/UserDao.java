package com.chrislai.springbootmall.dao;

import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
