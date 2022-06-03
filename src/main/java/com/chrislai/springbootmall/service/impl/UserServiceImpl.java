package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.dao.UserDao;
import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;
import com.chrislai.springbootmall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest request){
        return userDao.createUser(request);

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
