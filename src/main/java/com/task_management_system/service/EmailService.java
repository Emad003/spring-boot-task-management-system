package com.task_management_system.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String email,String token){
        String verificationLink = "http://localhost:8080/auth/verify?token=" + token; SimpleMailMessage message =
                new SimpleMailMessage(); message.setTo(email); message.setSubject("Verify your email");
                message.setText( "Welcome!\n\n" + "Please click the following link to verify your email:\n\n" +
                        verificationLink + "\n\n" + "This link is used to activate your account." ); mailSender.send(message);
        mailSender.send(message);
    }

}
