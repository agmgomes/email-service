package com.agmgomes.emailservice.infrastructure.adapters.in.rest.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.agmgomes.emailservice.domain.exception.EmailNotFoundException;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.exception.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Map<String, String>> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("message", fieldError.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                e.getStatusCode(),
                "Validation Error",
                "Validation failed",
                fieldErrors);

        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getStatusCode(),
                e);

        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND,
            "Invalid email ID.",
            e.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }
}
