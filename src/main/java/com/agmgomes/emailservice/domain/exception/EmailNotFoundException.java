package com.agmgomes.emailservice.domain.exception;

public class EmailNotFoundException extends RuntimeException {
  public EmailNotFoundException(Long id) {
    super("Email with id: " + id + " not found");
  }
}
