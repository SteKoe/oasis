package de.stekoe.idss.service.impl;

import de.stekoe.idss.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * The mail service.
 */
@Service("mailService")
public class MailService implements IMailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
