package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.dao.UserDao;
import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;
import com.chrislai.springbootmall.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest request){
        User user = userDao.getUserByEmail(request.getEmail());
        if(user != null)
        {
            log.error("該EMAIL:{}已被註冊",request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userDao.createUser(request);

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
