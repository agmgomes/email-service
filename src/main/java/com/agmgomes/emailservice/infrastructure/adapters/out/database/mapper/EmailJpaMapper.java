package com.agmgomes.emailservice.infrastructure.adapters.out.database.mapper;

import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.domain.model.EmailMessage;
import com.agmgomes.emailservice.domain.model.EmailStatus;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.entities.EmailJpaEntity;
import com.agmgomes.emailservice.infrastructure.adapters.out.database.entities.EmailStatusJpaEntity;

@Component
public class EmailJpaMapper {
    public EmailJpaEntity toEmailJpaEntity(EmailMessage emailMessage) {
        EmailJpaEntity jpaEntity = new EmailJpaEntity();
        jpaEntity.setId(emailMessage.getId());
        jpaEntity.setRecipient(emailMessage.getRecipient());
        jpaEntity.setSubject(emailMessage.getSubject());
        jpaEntity.setBody(emailMessage.getBody());
        jpaEntity.setCreatedAt(emailMessage.getCreatedAt());
        jpaEntity.setUpdatedAt(emailMessage.getUpdatedAt());
        jpaEntity.setSendAt(emailMessage.getSendAt());
        jpaEntity.setStatus(toStatusJpaEntity(emailMessage.getStatus()));

        return jpaEntity;
    }

    public EmailMessage toEmailDomain(EmailJpaEntity emailJpaEntity) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setId(emailJpaEntity.getId());
        emailMessage.setRecipient(emailJpaEntity.getRecipient());
        emailMessage.setSubject(emailJpaEntity.getSubject());
        emailMessage.setBody(emailJpaEntity.getBody());
        emailMessage.setCreatedAt(emailJpaEntity.getCreatedAt());
        emailMessage.setUpdatedAt(emailJpaEntity.getUpdatedAt());
        emailMessage.setSendAt(emailJpaEntity.getSendAt());
        emailMessage.setStatus(toStatusDomain(emailJpaEntity.getStatus()));
    
        return emailMessage;
    }

    public EmailStatusJpaEntity toStatusJpaEntity(EmailStatus emailStatus) {
        if (emailStatus == null) {
            return null;
        }

        EmailStatusJpaEntity statusEntity = new EmailStatusJpaEntity();
        statusEntity.setId(emailStatus.getId());
        statusEntity.setStatus(emailStatus.getStatus());

        return statusEntity;
    }

    public EmailStatus toStatusDomain(EmailStatusJpaEntity statusJpaEntity) {
        EmailStatus statusDomain = new EmailStatus();
        statusDomain.setId(statusJpaEntity.getId());
        statusDomain.setStatus(statusJpaEntity.getStatus());
        return statusDomain;
    }

}
