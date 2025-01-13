package com.agmgomes.emailservice.infrastructure.adapters.out.database;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.application.ports.out.EmailDatabasePort;
import com.agmgomes.emailservice.domain.exception.EmailNotFoundException;
import com.agmgomes.emailservice.domain.model.EmailMessage;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.entities.EmailJpaEntity;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.mapper.EmailJpaMapper;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.repositories.EmailJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailJpaDatabaseAdapter implements EmailDatabasePort {

    private final EmailJpaRepository emailJpaRepository;
    private final EmailJpaMapper emailJpaMapper;

    @Override
    public List<EmailMessage> findScheduledEmails() {
        List<EmailJpaEntity> scheduledEmailsJpa = emailJpaRepository.findByStatus_StatusAndSendAtBefore("pending",
                LocalDateTime.now());

        return scheduledEmailsJpa.stream()
                .map(emailJpaMapper::toEmailDomain)
                .collect(Collectors.toList());
    }

    @Override
    public EmailMessage getEmailInfo(Long emailId) {
        EmailJpaEntity foundEmail = emailJpaRepository.findById(emailId)
                .orElseThrow(() -> new EmailNotFoundException(emailId));

        return emailJpaMapper.toEmailDomain(foundEmail);

    }

    @Override
    public EmailMessage saveEmail(EmailMessage emailMessage) {
        EmailJpaEntity entity = emailJpaMapper.toEmailJpaEntity(emailMessage);
        EmailJpaEntity savedEmail = this.emailJpaRepository.save(entity);
        return emailJpaMapper.toEmailDomain(savedEmail);
    }

}
