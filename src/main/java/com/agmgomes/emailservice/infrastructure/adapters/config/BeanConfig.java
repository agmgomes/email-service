package com.agmgomes.emailservice.infrastructure.adapters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agmgomes.emailservice.application.ports.out.EmailSenderPort;
import com.agmgomes.emailservice.infrastructure.adapters.out.aws.AwsSesAdapter;
import com.agmgomes.emailservice.infrastructure.adapters.out.sendgrid.SendGridAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BeanConfig {

    @Value("${email.provider}")
    private String emailProvider;

    private final SendGridAdapter sendGridAdapter;
    private final AwsSesAdapter awsSesAdapter;

    @Bean
    public EmailSenderPort emailSenderPort() {
        if("sendgrid".equals(emailProvider)) {
            log.info("Email Provider Port: SendGrid Adapter.");
            return sendGridAdapter;
        } else if("aws".equals(emailProvider)) {
            log.info("Email Provider Port: AWS SES Adapter.");
            return awsSesAdapter;
        }
        else {
            throw new IllegalArgumentException("Provider not supported");
        }
    }

}
