package org.example.orderservice.listener.kafka;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.commons.dto.StatusEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderStatusListener {
    @KafkaListener(topics = "${spring.kafka.topic.status}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(@NonNull ConsumerRecord<Long, StatusEvent> record) {
        log.info("Received message: {}", record.value());
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}",
                 record.key(),
                 record.partition(),
                 record.topic(),
                 record.timestamp());
    }
}
