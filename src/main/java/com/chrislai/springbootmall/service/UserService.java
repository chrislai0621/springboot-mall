package com.chrislai.springbootmall.service;

import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest request);
    User getUserById(Integer userId);
}
