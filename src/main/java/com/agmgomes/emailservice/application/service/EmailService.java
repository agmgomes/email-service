package com.agmgomes.emailservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agmgomes.emailservice.application.ports.in.GetEmailInfoUseCase;
import com.agmgomes.emailservice.application.ports.in.SaveEmailUseCase;
import com.agmgomes.emailservice.application.ports.in.SendEmailUseCase;
import com.agmgomes.emailservice.application.ports.in.SendScheduledEmailUseCase;
import com.agmgomes.emailservice.application.ports.out.EmailDatabasePort;
import com.agmgomes.emailservice.application.ports.out.EmailQueuePort;
import com.agmgomes.emailservice.application.ports.out.EmailSenderPort;
import com.agmgomes.emailservice.domain.model.EmailMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService implements
        SaveEmailUseCase,
        GetEmailInfoUseCase,
        SendEmailUseCase,
        SendScheduledEmailUseCase {

    private final EmailSenderPort emailSenderPort;
    private final EmailQueuePort emailQueuePort;
    private final EmailDatabasePort emailDatabasePort;

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        try {
            this.emailSenderPort.sendEmail(emailMessage);

            emailMessage.markAsSuccess();
            emailDatabasePort.saveEmail(emailMessage);
        } catch (Exception e) {
            log.error("Failed to send email with id: {}. Error: {}", emailMessage.getId(), e.getMessage());

            emailMessage.markAsFailed();
            emailDatabasePort.saveEmail(emailMessage);
        }
    }

    @Override
    public EmailMessage getEmailInfo(Long id) {
        EmailMessage foundEmail = emailDatabasePort.getEmailInfo(id);
        return foundEmail;
    }

    @Override
    public EmailMessage saveEmail(EmailMessage emailMessage) {
        log.info("Email with id: {} saved into the database.", emailMessage.getId());
        return this.emailDatabasePort.saveEmail(emailMessage);
    }

    @Override
    public void processScheduledEmails() {
        List<EmailMessage> scheduledEmails = emailDatabasePort.findScheduledEmails();

        for (EmailMessage emailMessage : scheduledEmails) {
            try {
                this.emailQueuePort.scheduleEmail(emailMessage);

                emailMessage.markAsQueued();
                emailDatabasePort.saveEmail(emailMessage);
            } catch (Exception e) {
                log.error("Failed to queue email with id: {}", emailMessage.getId());

                emailMessage.markAsFailed();
                emailDatabasePort.saveEmail(emailMessage);
            }
        }
    }

}
