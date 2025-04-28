package com.mkruger.email_service.core.valueobjects;

public record EmailMessage(String recipient, String subject, String body) {
	public EmailMessage {
		if (recipient == null || recipient.isBlank()) {
			throw new IllegalArgumentException("Recipient email cannot be null or empty");
		}
		if (subject == null || subject.isBlank()) {
			throw new IllegalArgumentException("Subject cannot be null or empty");
		}
		if (body == null || body.isBlank()) {
			throw new IllegalArgumentException("Body cannot be null or empty");
		}
	}
}
