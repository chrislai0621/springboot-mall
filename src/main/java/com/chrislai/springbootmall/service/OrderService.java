package com.chrislai.springbootmall.service;

import com.chrislai.springbootmall.dto.CreateOrderRequest;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest request);
}
