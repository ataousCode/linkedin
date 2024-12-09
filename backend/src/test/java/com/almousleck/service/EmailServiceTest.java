package com.almousleck.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendEmailSuccessfully() throws MessagingException, UnsupportedEncodingException {
        // Given
        String email = "test@example.com";
        String subject = "Test Email";
        String content = "This is a test email.";
        EmailService emailService = new EmailService(javaMailSender);

        // When
        emailService.sendEmail(email, subject, content);

        // Then
        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void shouldThrowUnsupportedEncodingExceptionWhenContentContainsNonAsciiCharacters() {
    // Given
    String email = "test@example.com";
    String subject = "Test Email";
    String content = "This is a test email with non-ASCII characters: éáúíó";
    EmailService emailService = new EmailService(javaMailSender);

    // When
    try {
        emailService.sendEmail(email, subject, content);
        fail("Expected UnsupportedEncodingException to be thrown");
    } catch (UnsupportedEncodingException e) {
        // Then
        assertEquals(UnsupportedEncodingException.class, e.getClass());
    } catch (Exception e) {
        fail("Expected UnsupportedEncodingException to be thrown, but got " + e.getClass());
    }
}
}