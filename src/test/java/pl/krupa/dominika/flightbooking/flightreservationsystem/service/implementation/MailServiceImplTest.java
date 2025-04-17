package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMail() {
        String toEmail = "test@example.com";
        String subject = "Flight Booking Confirmation";
        String body = "Your flight booking is confirmed.";

        mailService.sendMail(toEmail, subject, body);

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(toEmail);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(body);
        expectedMessage.setFrom(MailServiceImpl.SENDER_MAIL);

        verify(mailSender, times(1)).send(expectedMessage);
    }

    @Test
    void testSendMail_ExceptionThrown() {
        String toEmail = "test@example.com";
        String subject = "Flight Booking Confirmation";
        String body = "Your flight booking is confirmed.";

        doThrow(new MailSendException("Error sending email")).when(mailSender).send(Mockito.any(SimpleMailMessage.class));

        MailSendException exception = assertThrows(MailSendException.class, () -> {
            mailService.sendMail(toEmail, subject, body);
        });

        assertEquals("Error sending email", exception.getMessage());
    }
}
