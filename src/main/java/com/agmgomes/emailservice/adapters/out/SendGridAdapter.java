package com.agmgomes.emailservice.adapters.out;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.core.model.EmailMessage;
import com.agmgomes.emailservice.core.ports.EmailProviderPort;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Component
public class SendGridAdapter implements EmailProviderPort {
    private final Logger logger = LoggerFactory.getLogger(SendGridAdapter.class);

    private SendGrid sendGrid;
    private String defaultSender;

    public SendGridAdapter(
            SendGrid sendGrid,
            @Qualifier("sendGridDefaultSender") String defaultSender) {
        this.sendGrid = sendGrid;
        this.defaultSender = defaultSender;
    }

    @Override
    public void send(EmailMessage email) throws IOException {
        logger.info("Sending email to: {} via SendGrid: {}", email.getTo(), email.getSubject());

        Email from = new Email(defaultSender);
        String subject = email.getSubject();
        Email to = new Email(email.getTo());
        Content content = new Content("text/plain", email.getBody());
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            logger.info("Response code: {}", response.getStatusCode());
            logger.info("Response body: {}", response.getBody());
            logger.info("Response headers: {}", response.getHeaders());

            logger.info("Email sent via SendGrid.");

        } catch (IOException e) {
            logger.error("Error sending email through SendGrid", e.getLocalizedMessage());
            throw e;
        }

    }
}
