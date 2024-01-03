package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.model.Order;
import org.example.orderservice.service.kafka.OrderKafkaService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderKafkaService kafkaService;

    private final Map<Long, Order> orders = new HashMap<>();

    public void createOrder(Order order) {
        long index = orders.size();
        orders.put(index, order);
        kafkaService.sendOrder(order);
    }
}
