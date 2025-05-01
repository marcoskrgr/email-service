package com.mkruger.email_service.application.usecases;

import com.mkruger.email_service.application.ports.in.SendEmailUseCase;
import com.mkruger.email_service.application.ports.out.EmailSenderGateway;
import com.mkruger.email_service.core.valueobjects.EmailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendEmailUseCaseImpl implements SendEmailUseCase {

	private final EmailSenderGateway emailSenderGateway;

	public SendEmailUseCaseImpl(EmailSenderGateway emailSenderGateway) {
		this.emailSenderGateway = emailSenderGateway;
	}

	@Override
	@Async
	public void sendEmail(EmailMessage emailMessage) {
		emailSenderGateway.sendEmail(emailMessage);
	}
}
