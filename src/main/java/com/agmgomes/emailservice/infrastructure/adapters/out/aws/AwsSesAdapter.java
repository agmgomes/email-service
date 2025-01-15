package com.agmgomes.emailservice.infrastructure.adapters.out.aws;

import org.springframework.stereotype.Component;

import com.agmgomes.emailservice.application.ports.out.EmailSenderPort;
import com.agmgomes.emailservice.domain.exception.EmailSendingException;
import com.agmgomes.emailservice.domain.model.EmailMessage;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

@Slf4j
@Component
public class AwsSesAdapter implements EmailSenderPort {
        private SesClient sesClient;
        private String awsVerifiedSender;

        public AwsSesAdapter(SesClient sesClient, String awsVerifiedSender) {
                this.sesClient = sesClient;
                this.awsVerifiedSender = awsVerifiedSender;
        }

        @Override
        public void sendEmail(EmailMessage emailMessage) {
                Destination destination = Destination.builder()
                                .toAddresses(emailMessage.getRecipient())
                                .build();

                Content subject = Content.builder()
                                .data(emailMessage.getSubject())
                                .build();

                Content content = Content.builder()
                                .data(emailMessage.getBody())
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
                                .source(awsVerifiedSender)
                                .build();

                try {
                        log.info("Attempting to send email with id: {} through Amazon SES.",
                                        emailMessage.getId());
                        sesClient.sendEmail(emailRequest);
                        log.info("Email successfully sent to: {} via Amazon SES.",
                                        emailMessage.getRecipient());
                } catch (SesException e) {
                        log.error("Error sending email with id: {} through Amazon SES. Error : {}",
                                        emailMessage.getId(), e.awsErrorDetails().errorMessage());
                        throw new EmailSendingException("Failed to send email using Amazon SES", e);
                }
        }
}
