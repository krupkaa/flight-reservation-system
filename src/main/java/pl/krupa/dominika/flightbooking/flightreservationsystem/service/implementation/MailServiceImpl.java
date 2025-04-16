package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.MailService;

@Service
public class MailServiceImpl implements MailService {

    public final static String SENDER_MAIL = "sendmail.flightBooking@gmail.com";

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendMail(String toEmail,
                           String subject,
                           String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(SENDER_MAIL);
        mailSender.send(message);
    }
}
