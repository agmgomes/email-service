package com.agmgomes.emailservice.application.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.agmgomes.emailservice.application.ports.in.SendScheduledEmailUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailCronJob {
    private final SendScheduledEmailUseCase sendScheduledEmailUseCase;

    @Scheduled(fixedRate = 60000)
    public void processPendingEmails() {
        log.info("Email Cron Job - {}", LocalDateTime.now());
        log.info("Processing Scheduled Emails.");
        sendScheduledEmailUseCase.processScheduledEmails();
    }

}
