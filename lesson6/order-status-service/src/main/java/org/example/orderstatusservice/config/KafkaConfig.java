package org.example.orderstatusservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Value(value = "${spring.kafka.topic.order}")
    private String orderTopic;

    @Value(value = "${spring.kafka.topic.status}")
    private String statusTopic;

    @Bean
    public NewTopic orderTopic() {
        return new NewTopic(orderTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic statusTopic() {
        return new NewTopic(statusTopic, 1, (short) 1);
    }
}
