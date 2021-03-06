package com.chrislai.springbootmall.dao;

import com.chrislai.springbootmall.dto.OrderQueryParams;
import com.chrislai.springbootmall.model.Order;
import com.chrislai.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);


}

