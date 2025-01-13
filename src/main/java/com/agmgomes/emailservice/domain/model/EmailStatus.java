package com.agmgomes.emailservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailStatus {
    private Long id;
    private String status;
    
    @Getter
    @AllArgsConstructor
    public enum Values {
        PENDING(1L, "pending"),
        QUEUED(2L, "queued"),
        SUCCESS(3L, "success"),
        FAILED(4L, "failed");

        private Long id;
        private String status;

        public EmailStatus toStatus() {
            return new EmailStatus(id, status);
        }
    }
}
