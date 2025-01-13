package com.agmgomes.emailservice.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmailMessage {
    private Long id;
    private String recipient;
    private String subject;
    private String body;
    private LocalDateTime sendAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EmailStatus status;

    public void markAsSuccess() {
        this.updatedAt = LocalDateTime.now();
        this.status = EmailStatus.Values.SUCCESS.toStatus();
    }

    public void markAsQueued() {
        this.updatedAt = LocalDateTime.now();
        this.status = EmailStatus.Values.QUEUED.toStatus();
    }

    public void markAsFailed() {
        this.updatedAt = LocalDateTime.now();
        this.status = EmailStatus.Values.FAILED.toStatus();
    }
}
