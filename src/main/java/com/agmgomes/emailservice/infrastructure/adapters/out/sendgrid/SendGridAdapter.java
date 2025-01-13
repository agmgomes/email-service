package com.agmgomes.emailservice.infrastructure.adapters.out.sendgrid;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.application.ports.out.EmailSenderPort;
import com.agmgomes.emailservice.domain.exception.EmailSendingException;
import com.agmgomes.emailservice.domain.model.EmailMessage;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendGridAdapter implements EmailSenderPort {
    
    private SendGrid sendGrid;
    private String defaultSender;

    public SendGridAdapter(
        SendGrid sendGrid,
        @Qualifier("sendGridDefaultSender") String defaultSender
        ) {
        this.sendGrid = sendGrid;
        this.defaultSender = defaultSender;
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        log.info("Sending email to: {} via SendGrid: {}", emailMessage.getRecipient(), emailMessage.getSubject());
        
        Email from = new Email(defaultSender);
        String subject = emailMessage.getSubject();
        Email to = new Email(emailMessage.getRecipient());
        Content content = new Content("text/plain", emailMessage.getBody());
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            log.info("Response code: {}", response.getStatusCode());
            log.info("Response body: {}", response.getBody());
            log.info("Response headers: {}", response.getHeaders());

            log.info("Email sent via SendGrid.");

        } catch (IOException e) {
            log.error("Error sending email through SendGrid", e.getLocalizedMessage());
            throw new EmailSendingException("Failed to send email using SendGrid", e);
        }

    }
}
