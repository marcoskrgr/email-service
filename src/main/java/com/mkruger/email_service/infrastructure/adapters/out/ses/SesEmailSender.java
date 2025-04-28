package com.mkruger.email_service.infrastructure.adapters.out.ses;

import com.mkruger.email_service.core.exceptions.EmailServiceException;
import com.mkruger.email_service.application.ports.out.EmailSenderGateway;
import com.mkruger.email_service.core.valueobjects.EmailMessage;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SesException;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SesEmailSender implements EmailSenderGateway {

    private static final Logger logger = LoggerFactory.getLogger(SesEmailSender.class);

    private final SesClient sesClient;
    private final String sourceEmail;

    public SesEmailSender(
            SesClient sesClient,
            @Value("${aws.ses.source-email}") String sourceEmail) {
        this.sesClient = sesClient;
        this.sourceEmail = sourceEmail;
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        try {
            SendEmailRequest request = createEmailRequest(emailMessage);
            SendEmailResponse response = sesClient.sendEmail(request);
            logger.info("Email sent successfully. Message ID: {}", response.messageId());
        } catch (SesException e) {
            logger.error("Failed to send email: {}", e.getMessage(), e);
            throw new EmailServiceException("Failed to send email", e);
        }
    }

    private SendEmailRequest createEmailRequest(EmailMessage emailMessage) {
        Destination destination = Destination.builder()
                .toAddresses(emailMessage.recipient())
                .build();

        Content subjectContent = Content.builder()
                .charset("UTF-8")
                .data(emailMessage.subject())
                .build();

        Content bodyContent = Content.builder()
                .charset("UTF-8")
                .data(emailMessage.body())
                .build();

        Body messageBody = Body.builder()
                .text(bodyContent)
                .build();

        Message message = Message.builder()
                .subject(subjectContent)
                .body(messageBody)
                .build();

        return SendEmailRequest.builder()
                .source(sourceEmail)
                .destination(destination)
                .message(message)
                .build();
    }
}
