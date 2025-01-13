package com.agmgomes.emailservice.infrastructure.adapters.out.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

import com.agmgomes.emailservice.domain.exception.EmailSendingException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    @Override
    public boolean isFatal(@SuppressWarnings("null") Throwable t) {
        ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
        
        String queueName = lefe
            .getFailedMessage()
            .getMessageProperties()
            .getConsumerQueue();
        
        Message failedMessage = lefe.getFailedMessage();

        if(t.getCause() instanceof EmailSendingException || t.getCause() instanceof ConstraintViolationException) {
            log.error("Message rejected: {} from queue {}.", failedMessage, queueName);
            return true;
        }
        
        return super.isFatal(t);
    }

}
