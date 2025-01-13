package com.agmgomes.emailservice.infrastructure.adapters.out.database.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agmgomes.emailservice.infrastructure.adapters.out.database.entities.EmailJpaEntity;

@Repository
public interface EmailJpaRepository extends JpaRepository<EmailJpaEntity, Long> {
    List<EmailJpaEntity> findByStatus_StatusAndSendAtBefore(String status, LocalDateTime sendAt);
}
