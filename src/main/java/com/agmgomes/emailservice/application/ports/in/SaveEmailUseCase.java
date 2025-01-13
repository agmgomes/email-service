package com.agmgomes.emailservice.application.ports.in;

import com.agmgomes.emailservice.domain.model.EmailMessage;

public interface SaveEmailUseCase {
    EmailMessage saveEmail(EmailMessage emailMessage);
}
