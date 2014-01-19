package de.stekoe.idss.service;

import org.springframework.stereotype.Service;

/**
 * Allows to send mails.
 */
@Service
public interface MailService {
    /**
     * @param to The receipient
     * @param subject The subject of the mail
     * @param message The message of the mail
     */
    void sendMail(String to, String subject, String message);
}
