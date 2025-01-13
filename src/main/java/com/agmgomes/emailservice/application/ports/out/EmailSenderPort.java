package com.agmgomes.emailservice.application.ports.out;

import com.agmgomes.emailservice.domain.model.EmailMessage;

public interface EmailSenderPort {
    public void sendEmail(EmailMessage email);    
}
