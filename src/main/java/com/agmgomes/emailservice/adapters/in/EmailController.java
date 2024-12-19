package com.agmgomes.emailservice.adapters.in;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agmgomes.emailservice.application.dto.EmailDTO;
import com.agmgomes.emailservice.core.ports.EmailSenderPort;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final EmailSenderPort emailSenderPort;

    public EmailController(EmailSenderPort emailSenderPort) {
        this.emailSenderPort = emailSenderPort;
    }

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailDTO emailDTO) throws IOException {
        logger.info("Accessing endpoint POST /api/emails");
        logger.info("Sending Email DTO to Email Sender Port.");
        emailSenderPort.sendEmail(emailDTO);
        return ResponseEntity.ok("Email sent with success");
    }
}
