package com.agmgomes.emailservice.core.usecases;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.agmgomes.emailservice.application.dto.EmailDTO;
import com.agmgomes.emailservice.core.model.EmailMessage;
import com.agmgomes.emailservice.core.ports.EmailProviderPort;
import com.agmgomes.emailservice.core.ports.EmailSenderPort;

@Service
public class SendEmailUseCase implements EmailSenderPort {
    private final EmailProviderPort emailProviderPort;
    
    public SendEmailUseCase(EmailProviderPort emailProviderPort) {
        this.emailProviderPort = emailProviderPort;
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) throws IOException {
        EmailMessage email = new EmailMessage(emailDTO.to(), emailDTO.subject(), emailDTO.body());
        emailProviderPort.send(email);      
    }
   
}
