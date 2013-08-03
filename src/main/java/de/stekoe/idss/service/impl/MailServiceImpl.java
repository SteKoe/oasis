package de.stekoe.idss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import de.stekoe.idss.WicketApplication;
import de.stekoe.idss.service.MailService;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        // Prevent sending mails in development mode
        if(!WicketApplication.isDevelopmentMode()) {
            mailSender.send(message);
        }
    }

}
