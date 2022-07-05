package com.chrislai.springbootmall.controller;

import com.chrislai.springbootmall.dto.CreateOrderRequest;
import com.chrislai.springbootmall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Integer> createOrder(@PathVariable Integer userId,
                                       @RequestBody @Valid CreateOrderRequest request) {
        Integer orderId = orderService.createOrder(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }
}
