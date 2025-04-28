package com.mkruger.email_service.application.ports.in;

import com.mkruger.email_service.core.valueobjects.EmailMessage;

public interface SendEmailUseCase {
    void sendEmail(EmailMessage emailMessage);
}
