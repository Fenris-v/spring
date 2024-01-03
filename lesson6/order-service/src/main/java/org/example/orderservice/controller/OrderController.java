package org.example.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
