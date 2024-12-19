package com.agmgomes.emailservice.adapters.out;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.core.model.EmailMessage;
import com.agmgomes.emailservice.core.ports.EmailProviderPort;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

@Component
public class AwsSesAdapter implements EmailProviderPort {
    private final Logger logger = LoggerFactory.getLogger(AwsSesAdapter.class);

    private SesClient sesClient;
    private String defaultSender;

    public AwsSesAdapter(SesClient sesClient, @Qualifier("awsDefaultSender") String defaultSender) {
        this.sesClient = sesClient;
        this.defaultSender = defaultSender;
    }

    @Override
    public void send(EmailMessage email) throws IOException {
        Destination destination = Destination.builder()
                .toAddresses(email.getTo())
                .build();

        Content subject = Content.builder()
                .data(email.getSubject())
                .build();

        Content content = Content.builder()
                .data(email.getBody())
                .build();

        Body body = Body.builder()
                .text(content)
                .build();

        Message message = Message.builder()
                .subject(subject)
                .body(body)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .message(message)
                .source(defaultSender)
                .build();
                
        try {
            logger.info("Attempting to send email through Amazon SES.");
            sesClient.sendEmail(emailRequest);
            logger.info("Email successfully sent to: {} via Amazon SES.", email.getTo());
        } catch (SesException e) {
            logger.error("Error sending email through Amazon SES", e.awsErrorDetails().errorMessage());
            throw e;
        }
    }
}
