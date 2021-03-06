package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.dao.UserDao;
import com.chrislai.springbootmall.dto.UserLoginRequest;
import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;
import com.chrislai.springbootmall.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest request) {
        User user = userDao.getUserByEmail(request.getEmail());
        if (user != null) {
            log.warn("該EMAIL:{}已被註冊", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5 生成密碼的HASH
        String hashedPassword = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());
        request.setPassword(hashedPassword);
        return userDao.createUser(request);

    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest request) {
        User user = userDao.getUserByEmail(request.getEmail());
        if (user == null) {
            log.warn("該EMAIL:{}尚未註冊", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.warn("email : {} 的密碼不正確", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
