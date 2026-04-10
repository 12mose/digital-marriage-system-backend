package com.ishimwe.digitalmarriagesystem.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {
        String subject = "Email Verification - Digital Marriage System";
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
        
        String message = "Welcome to the Digital Marriage Registration System!\n\n" +
                "Please click the link below to verify your email address and activate your account:\n" +
                verificationUrl + "\n\n" +
                "If you did not register for this account, please ignore this email.";

        sendEmail(to, subject, message);
    }

    public void sendStatusUpdateEmail(String to, String applicationNumber, String newStatus) {
        String subject = "Application Status Update - Digital Marriage System";
        String message = "Dear Citizen,\n\n" +
                "The status of your marriage application (#" + applicationNumber + ") has been updated to: " + newStatus.toUpperCase() + ".\n\n" +
                "Please log in to your dashboard to view more details.\n\n" +
                "Best regards,\n" +
                "Digital Marriage Registration Team";

        sendEmail(to, subject, message);
    }

    private void sendEmail(String to, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("no-reply@dms.com");

        mailSender.send(email);
    }
}
