package com.agmgomes.emailservice.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agmgomes.emailservice.application.ports.in.GetEmailInfoUseCase;
import com.agmgomes.emailservice.application.ports.in.SaveEmailUseCase;
import com.agmgomes.emailservice.domain.model.EmailMessage;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.request.EmailDTO;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.request.ScheduledEmailDTO;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.dto.response.EmailResponseDTO;
import com.agmgomes.emailservice.infrastructure.adapters.in.rest.mapper.EmailRestMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/emails")
public class EmailRestAdapter {
    private final SaveEmailUseCase saveEmailUseCase;
    private final GetEmailInfoUseCase getEmailInfoUseCase;
    private final EmailRestMapper emailMapper;

    @PostMapping("/send")
    public ResponseEntity<EmailResponseDTO> sendEmail(@RequestBody @Valid EmailDTO emailDTO) {
        log.info("Accessing endpoint POST /api/emails/send");

        EmailMessage emailMessage = emailMapper.toEmailMessage(emailDTO);
        EmailMessage savedEmail = saveEmailUseCase.saveEmail(emailMessage);

        EmailResponseDTO responseDTO = emailMapper.toEmailResponseDTO(savedEmail);
        
        return ResponseEntity.accepted().body(responseDTO);
    }

    @PostMapping("/schedule")
    public ResponseEntity<EmailResponseDTO> sendScheduledEmail(@RequestBody @Valid ScheduledEmailDTO scheduledEmailDTO) {
        log.info("Accessing endpoint POST /api/emails/schedule");
        
        EmailMessage emailMessage = emailMapper.toEmailMessage(scheduledEmailDTO);
        EmailMessage savedEmail = saveEmailUseCase.saveEmail(emailMessage);

        EmailResponseDTO responseDTO = emailMapper.toEmailResponseDTO(savedEmail);
        
        return ResponseEntity.accepted().body(responseDTO);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmailResponseDTO> getEmailInfo(@PathVariable Long id) {
        log.info("Accessing endpoint GET /api/emails/id/");
        
        EmailMessage emailMessage = getEmailInfoUseCase.getEmailInfo(id);
        EmailResponseDTO emailResponseDTO = emailMapper.toEmailResponseDTO(emailMessage);
        
        return ResponseEntity.ok(emailResponseDTO);
    }
    
}
