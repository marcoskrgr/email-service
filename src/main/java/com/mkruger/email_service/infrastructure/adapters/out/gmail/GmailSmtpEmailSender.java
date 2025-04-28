package com.mkruger.email_service.infrastructure.adapters.out.gmail;

import com.mkruger.email_service.application.ports.out.EmailSenderGateway;
import com.mkruger.email_service.core.exceptions.EmailServiceException;
import com.mkruger.email_service.core.valueobjects.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Primary
public class GmailSmtpEmailSender implements EmailSenderGateway {

	private static final Logger logger = LoggerFactory.getLogger(GmailSmtpEmailSender.class);

	private final JavaMailSender javaMailSender;
	private final String sourceEmail;

	public GmailSmtpEmailSender(
			JavaMailSender mailSender,
            @Value("${gmail.smtp.source-email}") String sourceEmail) {
		this.javaMailSender = mailSender;
		this.sourceEmail = sourceEmail;
	}

	@Override
	public void sendEmail(EmailMessage emailMessage) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(sourceEmail);
			message.setTo(emailMessage.recipient());
			message.setSubject(emailMessage.subject());
			message.setText(emailMessage.body());
			javaMailSender.send(message);
			logger.info("Email sent successfully with Gmail SMTP.");
		} catch (Exception e) {
			logger.error("Failed to send email with Gmail SMTP: {}", e.getMessage(), e);
			throw new EmailServiceException("Failed to send email with Gmail SMTP.", e);
		}
	}
}
