package com.agmgomes.emailservice.core.ports;

import java.io.IOException;

import com.agmgomes.emailservice.core.model.EmailMessage;

public interface EmailProviderPort {
    public void send(EmailMessage email) throws IOException;
}
