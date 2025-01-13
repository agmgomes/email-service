package com.agmgomes.emailservice.application.ports.in;

import com.agmgomes.emailservice.domain.model.EmailMessage;

public interface SendEmailUseCase {
    void sendEmail(EmailMessage emailMessage);
}
