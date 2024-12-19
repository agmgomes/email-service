package com.agmgomes.emailservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.agmgomes.emailservice.adapters.out.AwsSesAdapter;
import com.agmgomes.emailservice.adapters.out.SendGridAdapter;
import com.agmgomes.emailservice.core.ports.EmailProviderPort;

@Configuration
public class EmailConfig {
    private final Logger logger = LoggerFactory.getLogger(EmailConfig.class);

    @Value("${email.provider}")
    private String emailProvider;

    private final SendGridAdapter sendGridAdapter;
    private final AwsSesAdapter awsSesAdapter;

    public EmailConfig(SendGridAdapter sendGridAdapter, AwsSesAdapter awsSesAdapter) {
        this.sendGridAdapter = sendGridAdapter;
        this.awsSesAdapter = awsSesAdapter;
    }


    @Bean
    public EmailProviderPort emailProviderPort() {
        if("sendgrid".equals(emailProvider)) {
            logger.info("Email Provider Port: SendGrid Adapter.");
            return sendGridAdapter;
        } else if("aws".equals(emailProvider)) {
            logger.info("Email Provider Port: AWS SES Adapter.");
            return awsSesAdapter;
        }
        else {
            throw new IllegalArgumentException("Provider not supported");
        }
    }
}
