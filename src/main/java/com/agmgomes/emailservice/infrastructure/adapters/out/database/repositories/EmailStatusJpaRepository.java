package com.agmgomes.emailservice.infrastructure.adapters.out.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agmgomes.emailservice.infrastructure.adapters.out.database.entities.EmailStatusJpaEntity;

@Repository
public interface EmailStatusJpaRepository extends JpaRepository<EmailStatusJpaEntity, Long> {
    Optional<EmailStatusJpaEntity> findByStatus(String status);
}
