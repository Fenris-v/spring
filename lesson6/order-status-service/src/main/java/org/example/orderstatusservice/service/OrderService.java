package org.example.orderstatusservice.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.commons.dto.OrderEvent;
import org.example.commons.dto.StatusEvent;
import org.example.commons.enums.Status;
import org.example.orderstatusservice.service.kafka.OrderStatusService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderStatusService orderStatusService;

    @SneakyThrows
    public void process(OrderEvent orderEvent) {
        StatusEvent statusEvent = new StatusEvent(Status.CREATED, LocalDateTime.now());
        log.info("Order created {}", orderEvent);
        orderStatusService.sendEvent(statusEvent);

        Thread.sleep(2000);
        statusEvent = new StatusEvent(Status.PROCESS, LocalDateTime.now());
        orderStatusService.sendEvent(statusEvent);

        Thread.sleep(2000);
        statusEvent = new StatusEvent(Status.COMPLETED, LocalDateTime.now());
        orderStatusService.sendEvent(statusEvent);
    }
}
