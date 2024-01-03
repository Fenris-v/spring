package org.example.orderstatusservice.service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class AbstractKafkaService<T> implements KafkaService {
    @Autowired
    private KafkaTemplate<Long, T> kafkaTemplate;

    public void sendEvent(T dto) {
        kafkaTemplate.send(getTopic(), dto);
    }
}
