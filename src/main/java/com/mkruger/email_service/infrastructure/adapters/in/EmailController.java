package com.mkruger.email_service.infrastructure.adapters.in;

import com.mkruger.email_service.application.ports.in.SendEmailUseCase;
import com.mkruger.email_service.core.exceptions.EmailServiceException;
import com.mkruger.email_service.core.valueobjects.EmailMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

	private final SendEmailUseCase sendEmailUseCase;

	public EmailController(SendEmailUseCase sendEmailUseCase) {
		this.sendEmailUseCase = sendEmailUseCase;
	}

	@PostMapping()
	public ResponseEntity<String> sendEmail(@RequestBody EmailMessage emailMessage) {
		try {
			this.sendEmailUseCase.sendEmail(emailMessage);
			return ResponseEntity.ok("Email sent");
		} catch (EmailServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email: " + e.getMessage());
		}
	}
}
