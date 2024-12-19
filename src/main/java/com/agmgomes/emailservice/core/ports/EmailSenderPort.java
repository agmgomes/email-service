package com.agmgomes.emailservice.core.ports;

import java.io.IOException;

import com.agmgomes.emailservice.application.dto.EmailDTO;

public interface EmailSenderPort {
    public void sendEmail(EmailDTO emailDTO) throws IOException;    
}
