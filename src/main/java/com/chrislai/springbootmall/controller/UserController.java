package com.chrislai.springbootmall.controller;

import com.chrislai.springbootmall.dto.UserLoginRequest;
import com.chrislai.springbootmall.dto.UserRegisterRequest;
import com.chrislai.springbootmall.model.User;
import com.chrislai.springbootmall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest request) {
        Integer userId = userService.register(request);
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest request) {
        User user = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
