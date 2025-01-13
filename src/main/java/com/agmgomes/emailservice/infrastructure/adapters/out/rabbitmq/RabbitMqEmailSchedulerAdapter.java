package com.agmgomes.emailservice.infrastructure.adapters.out.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.application.ports.out.EmailQueuePort;
import com.agmgomes.emailservice.domain.model.EmailMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class RabbitMqEmailSchedulerAdapter implements EmailQueuePort {

    private final AmqpTemplate amqpTemplate;

    @Override
    public void scheduleEmail(EmailMessage emailMessage) {
        log.info("Publishing Scheduled Email with id: {} into RabbitMQ broker", emailMessage.getId());
        amqpTemplate.convertAndSend(
            RabbitMqConfig.EMAIL_EXCHANGE, 
            RabbitMqConfig.EMAIL_ROUTING_KEY, 
            emailMessage);
    } 
}
