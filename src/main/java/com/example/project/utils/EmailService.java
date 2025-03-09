package com.example.project.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendEmail(String to,String subject,String message)throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message,true);
        helper.setFrom("rakesh88577@gmail.com");

        mailSender.send(mimeMessage);
        System.out.println("Email sent Successfully");
    }


    public int generateRandomNumber(int digit){

        if(digit==6){
            return (int)(Math.random()*900000)+100000;
        }
        return (int)(Math.random()*9000)+1000;
    }
}
