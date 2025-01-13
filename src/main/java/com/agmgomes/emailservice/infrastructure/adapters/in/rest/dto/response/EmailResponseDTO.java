package com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.response;

import java.time.LocalDateTime;

public record EmailResponseDTO(Long id,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime sendAt) {

}
