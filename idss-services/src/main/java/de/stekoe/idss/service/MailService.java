package de.stekoe.idss.service;

/**
 * Allows to send mails.
 */
public interface MailService {
    /**
     * @param to The receipient
     * @param subject The subject of the mail
     * @param message The message of the mail
     */
    void sendMail(String to, String subject, String message);
}
