package com.chrislai.springbootmall.service.impl;

import com.chrislai.springbootmall.dao.OrderDao;
import com.chrislai.springbootmall.dao.ProductDao;
import com.chrislai.springbootmall.dao.UserDao;
import com.chrislai.springbootmall.dto.BuyItem;
import com.chrislai.springbootmall.dto.CreateOrderRequest;
import com.chrislai.springbootmall.model.Order;
import com.chrislai.springbootmall.model.OrderItem;
import com.chrislai.springbootmall.model.Product;
import com.chrislai.springbootmall.model.User;
import com.chrislai.springbootmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ProductDao productdao;
    private final UserDao userDao;

    @Override
    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest request) {
        //check user id exist
        User user = userDao.getUserById(userId);
        if (user == null) {
            log.warn("該USER ID {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int totalAmount = 0;
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (BuyItem item : request.getBuyItemList()) {
            Product product = productdao.getProductId(item.getProductId());
            if (product == null) {
                log.warn("商品 {} 不存在", item.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < item.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買，剩餘庫存 {} ，欲買數量 {}", product.getProductId(), product.getStock(), item.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //扣除商品庫存
            productdao.updateStock(product.getProductId(),product.getStock() - item.getQuantity());

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
