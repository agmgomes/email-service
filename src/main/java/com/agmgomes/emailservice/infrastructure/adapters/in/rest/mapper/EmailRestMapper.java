package com.agmgomes.emailservice.infrastructure.adapters.in.rest.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.domain.model.EmailMessage;
import com.agmgomes.emailservice.domain.model.EmailStatus;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.request.EmailDTO;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.request.ScheduledEmailDTO;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.response.EmailResponseDTO;

@Component
public class EmailRestMapper {
    public EmailMessage mapCommonFields(EmailMessage emailMessage, LocalDateTime now) {
        emailMessage.setCreatedAt(now);
        emailMessage.setUpdatedAt(now);
        emailMessage.setStatus(EmailStatus.Values.PENDING.toStatus());
        return emailMessage;
    }

    public EmailMessage toEmailMessage(EmailDTO emailDTO) {
        EmailMessage emailMessage = new EmailMessage();
        LocalDateTime now = LocalDateTime.now();

        emailMessage.setRecipient(emailDTO.recipient());
        emailMessage.setSubject(emailDTO.subject());
        emailMessage.setBody(emailDTO.body());
        emailMessage.setSendAt(now);

        return mapCommonFields(emailMessage, now);
    }

    public EmailMessage toEmailMessage(ScheduledEmailDTO scheduledEmailDTO) {
        EmailMessage emailMessage = new EmailMessage();
        LocalDateTime now = LocalDateTime.now();

        emailMessage.setRecipient(scheduledEmailDTO.recipient());
        emailMessage.setSubject(scheduledEmailDTO.subject());
        emailMessage.setBody(scheduledEmailDTO.body());
        emailMessage.setSendAt(scheduledEmailDTO.sendAt());

        return mapCommonFields(emailMessage, now);
    }

    public EmailResponseDTO toEmailResponseDTO(EmailMessage emailMessage) {
        return new EmailResponseDTO(
            emailMessage.getId(),
            emailMessage.getStatus().getStatus(),
            emailMessage.getCreatedAt(),
            emailMessage.getUpdatedAt(),
            emailMessage.getSendAt());
    }
}
