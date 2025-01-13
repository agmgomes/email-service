package com.agmgomes.emailservice.application.ports.out;

import com.agmgomes.emailservice.domain.model.EmailMessage;

public interface EmailQueuePort {
    void scheduleEmail(EmailMessage scheduledEmail);    
}
