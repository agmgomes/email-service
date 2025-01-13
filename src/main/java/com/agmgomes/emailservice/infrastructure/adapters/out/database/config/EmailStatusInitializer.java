package com.agmgomes.emailservice.infrastructure.adapters.out.database.config;

import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.domain.model.EmailStatus;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.entities.EmailStatusJpaEntity;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.repositories.EmailStatusJpaRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class EmailStatusInitializer {
    private final EmailStatusJpaRepository emailStatusJpaRepository;

    @PostConstruct
    public void initializeEmailStatus() {
        for(EmailStatus.Values status: EmailStatus.Values.values()) {
            emailStatusJpaRepository
                .findByStatus(status.getStatus())
                .orElseGet(() -> {
                    EmailStatusJpaEntity entity = new EmailStatusJpaEntity();
                    entity.setStatus(status.getStatus());
                    return emailStatusJpaRepository.save(entity); 
                });
        }
    }
}
