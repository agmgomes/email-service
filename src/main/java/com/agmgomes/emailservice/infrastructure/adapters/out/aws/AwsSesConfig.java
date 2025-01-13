package com.agmgomes.emailservice.infrastructure.adapters.out.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSesConfig {
    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.access.key}")
    private String awsAccessKey;

    @Value("${aws.secret.key}")
    private String awsSecretKey;

    @Value("${aws.verified.sender}")
    private String defaultSender;

    @Bean
    public SesClient sesClient() {
        StaticCredentialsProvider credentials = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(awsAccessKey, awsSecretKey));

        SesClient sesClient = SesClient.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(credentials)
                .build();

        return sesClient;
    }

    @Bean(name = "awsDefaultSender")
    public String awsDefaultSender() {
        return defaultSender;
    }
}
