package com.agmgomes.emailservice.domain.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message, Throwable cause) {
        super(message, cause, false, false);
    }   
}
