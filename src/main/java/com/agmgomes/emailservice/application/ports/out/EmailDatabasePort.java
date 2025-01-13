package com.agmgomes.emailservice.application.ports.out;

import java.util.List;

import com.agmgomes.emailservice.domain.model.EmailMessage;

public interface EmailDatabasePort {
    EmailMessage saveEmail(EmailMessage emailMessage);
    EmailMessage getEmailInfo(Long emailId);
    List<EmailMessage> findScheduledEmails();
}
