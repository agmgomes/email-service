package com.agmgomes.emailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;

@Configuration
public class SendGridConfig {

    @Value("${sendgrid.api.key}")
    private String apiKeyString;

    @Value("${sendgrid.verified.sender}")
    private String defaultSender;

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(apiKeyString);
    }

    @Bean(name = "sendGridDefaultSender")
    public String SendGridDefaultSender() {
        return defaultSender;
    }
}
