package org.example.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Value(value = "${spring.kafka.topic.order}")
    private String orderTopic;

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(orderTopic, 1, (short) 1);
    }
}
