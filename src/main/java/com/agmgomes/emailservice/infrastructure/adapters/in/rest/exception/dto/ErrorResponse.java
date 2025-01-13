package com.agmgomes.emailservice.infrastructure.adapters.in.rest.exception.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<Map<String, String>> fieldErrors;

    public ErrorResponse(HttpStatusCode status, String error, String message, List<Map<String, String>> fieldErrors) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = error;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(HttpStatusCode status, String error, Exception e) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = error;
        this.message = e.getMessage();
    }

    public ErrorResponse(HttpStatusCode status, Exception e) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = e.getLocalizedMessage();
        this.message = e.getMessage();
    }

    public ErrorResponse(HttpStatusCode status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = error;
        this.message = message;
    }
}
