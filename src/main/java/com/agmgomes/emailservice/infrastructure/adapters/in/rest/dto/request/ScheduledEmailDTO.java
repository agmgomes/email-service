package com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduledEmailDTO(
    @NotBlank
    @Email
    String recipient,
    @NotBlank
    String subject,
    @NotBlank
    String body,
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime sendAt
) {
    
}
