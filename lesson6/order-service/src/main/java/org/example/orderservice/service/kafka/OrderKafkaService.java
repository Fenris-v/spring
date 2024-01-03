package org.example.orderservice.service.kafka;

import org.example.commons.dto.OrderEvent;
import org.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaService extends AbstractKafkaService<OrderEvent> implements KafkaService {
    @Value("${spring.kafka.topic.order}")
    private String topic;

    @Override
    public String getTopic() {
        return topic;
    }

    public void sendOrder(@NonNull Order order) {
        OrderEvent orderEvent = new OrderEvent(order.getProduct(), order.getQuantity());
        sendEvent(orderEvent);
    }
}
