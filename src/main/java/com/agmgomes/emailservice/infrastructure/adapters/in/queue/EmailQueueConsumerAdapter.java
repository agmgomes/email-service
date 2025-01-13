package com.agmgomes.emailservice.infrastructure.adapters.in.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.agmgomes.emailservice.application.ports.in.SendEmailUseCase;
import com.agmgomes.emailservice.domain.model.EmailMessage;
import com.agmgomes.emailservice.infrastructure.adapters.out.rabbitmq.RabbitMqConfig;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Validated
@Component
public class EmailQueueConsumerAdapter {
    private final SendEmailUseCase sendEmailUseCase;

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE)
    public void consumeEmailMessage(@Valid @Payload EmailMessage emailMessage) {
        log.info("Processing email with id: {} from queue: {}.",
                emailMessage.getId(), RabbitMqConfig.EMAIL_QUEUE);
        sendEmailUseCase.sendEmail(emailMessage);
    }
}
