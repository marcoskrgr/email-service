package com.mkruger.email_service.application.ports.out;

import com.mkruger.email_service.core.valueobjects.EmailMessage;

public interface EmailSenderGateway {
    void sendEmail(EmailMessage emailMessage);
}
