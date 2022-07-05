package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.dao.OrderDao;
import com.chrislai.springbootmall.dao.ProductDao;
import com.chrislai.springbootmall.dto.BuyItem;
import com.chrislai.springbootmall.dto.CreateOrderRequest;
import com.chrislai.springbootmall.model.Order;
import com.chrislai.springbootmall.model.OrderItem;
import com.chrislai.springbootmall.model.Product;
import com.chrislai.springbootmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ProductDao productdao;

    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest request) {
        int totalAmount = 0;
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem item : request.getBuyItemList()) {
            Product product = productdao.getProductId(item.getProductId());
            int amount = item.getQuantity() * product.getPrice();
            totalAmount += amount;
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setAmount(amount);
            orderItemList.add(orderItem);
        }
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }
}
