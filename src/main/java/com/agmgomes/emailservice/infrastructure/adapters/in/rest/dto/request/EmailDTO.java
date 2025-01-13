package com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
    @NotBlank
    @Email
    String recipient,
    @NotBlank
    String subject,
    @NotBlank
    String body) { 
}
