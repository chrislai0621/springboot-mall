package com.chrislai.springbootmall.service;

import com.chrislai.springbootmall.dto.CreateOrderRequest;
import com.chrislai.springbootmall.dto.OrderQueryParams;
import com.chrislai.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest request);
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
}
