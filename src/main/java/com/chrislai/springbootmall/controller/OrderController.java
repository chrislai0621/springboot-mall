package com.chrislai.springbootmall.controller;

import com.chrislai.springbootmall.constant.ProductCategory;
import com.chrislai.springbootmall.dto.CreateOrderRequest;
import com.chrislai.springbootmall.dto.OrderQueryParams;
import com.chrislai.springbootmall.dto.ProductQueryRequest;
import com.chrislai.springbootmall.model.Order;
import com.chrislai.springbootmall.model.Product;
import com.chrislai.springbootmall.service.OrderService;
import com.chrislai.springbootmall.service.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable(required = true) Integer userId,
                                                     //取回幾筆資料
                                                     @RequestParam(defaultValue = "10" )@Max(1000) @Min(0) Integer limit,
                                                     //跳過幾筆數據
                                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        OrderQueryParams request = new OrderQueryParams();
        request.setUserId(userId);
        request.setLimit(limit);
        request.setOffset(offset);
        List<Order> orderList = orderService.getOrders(request);
        Integer total = orderService.countOrder(request);
        Page<Order> page =new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                       @RequestBody @Valid CreateOrderRequest request) {
        Integer orderId = orderService.createOrder(userId, request);
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
