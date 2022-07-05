package com.chrislai.springbootmall.service;

import com.chrislai.springbootmall.dto.CreateOrderRequest;
import com.chrislai.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest request);
    Order getOrderById(Integer orderId);
}
