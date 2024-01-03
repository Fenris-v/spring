package org.example.orderstatusservice.listener.kafka;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.OrderEvent;
import org.example.orderstatusservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {
    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(@NonNull OrderEvent orderEvent) {
        orderService.process(orderEvent);
    }
}
