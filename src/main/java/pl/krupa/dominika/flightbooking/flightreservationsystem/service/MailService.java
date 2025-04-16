package pl.krupa.dominika.flightbooking.flightreservationsystem.service;

public interface MailService {

    void sendMail(String toEmail, String subject, String body);
}
