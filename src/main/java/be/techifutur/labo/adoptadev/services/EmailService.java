package be.techifutur.labo.adoptadev.services;

public interface EmailService {

    void sendEmail(String to, String subject,String body);
}
