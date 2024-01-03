package org.example.orderstatusservice.service.kafka;

import org.example.commons.dto.StatusEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService extends AbstractKafkaService<StatusEvent> implements KafkaService {
    @Value("${spring.kafka.topic.status}")
    private String topic;

    @Override
    public String getTopic() {
        return topic;
    }
}
