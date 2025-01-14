package com.agmgomes.emailservice.infrastructure.adapters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agmgomes.emailservice.application.ports.out.EmailSenderPort;
import com.agmgomes.emailservice.infrastructure.adapters.out.aws.AwsSesAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BeanConfig {

    private final AwsSesAdapter awsSesAdapter;

    @Bean
    public EmailSenderPort emailSenderPort() {
        log.info("Email Sender Port: AWS SES Adapter.");
        return awsSesAdapter;
    }

}
