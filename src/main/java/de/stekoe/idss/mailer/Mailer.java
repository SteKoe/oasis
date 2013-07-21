package de.stekoe.idss.mailer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import de.stekoe.idss.WicketApplication;

public class Mailer {

    private static final Logger LOG = Logger.getLogger(Mailer.class);
    private MimeMessage msg;
    private String from;
    private Transport transport = null;
    private final Session mailSession;
    private final MimeMessage message;
    private String smtpUser;
    private String smtpPassword;
    private String content = "";

    public Mailer() {
        Properties props = readProperties();

        Authenticator auth = new SMTPAuthenticator();
        mailSession = Session.getDefaultInstance(props, auth);
        try {
            transport = mailSession.getTransport();
        } catch (NoSuchProviderException e) {
            LOG.error("Error creating transport layer for email!", e);
        }
        message = createMessage();
//        sendMessage();
    }

    private Properties readProperties()  {
        Properties props = new Properties();

        try {
            props.load(WicketApplication.class.getClassLoader().getResourceAsStream(WicketApplication.class.getSimpleName()+".properties"));
            smtpUser = props.getProperty("mail.smtp.user");
            smtpPassword = props.getProperty("mail.smtp.password");

        } catch (IOException e) {
            LOG.error("Could not load email configuration properties!", e);
        }
        return props;
    }

    public String renderTemplate() {
        MailTemplate template = new MailTemplate(Mailer.class,"mail.tpl");
        Map map = new HashMap();
        map.put("id", "asd");
        return template.interpolate(map).asString();
    }

    private MimeMessage createMessage() {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("This is a subject!");
            message.setContent(getContent(), "text/plain");
            message.setFrom(new InternetAddress("idss@stekoe.de"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("stekoe2000@yahoo.de"));
            return message;
        } catch (Exception e) {
            LOG.error("Error building email!", e);
        }

        return null;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String getContent() {
        return this.content;
    }

    private void sendMessage() {
        try {
            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException e) {
            LOG.error("Error sending email!", e);
        }
    }

    public void sendMail(String recipient, String subject, String message) {
        try {
            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);
        } catch (MessagingException me) {
            LOG.error(String.format("Invalid email adress from '%s'.", from),
                    me);
        }

        try {
            InternetAddress addressTo = new InternetAddress(recipient);
            msg.setRecipient(Message.RecipientType.TO, addressTo);
        } catch (MessagingException me) {
            LOG.error(String.format("Invalid email adress to '%s'.", from), me);
        }

        try {
            msg.setSubject(subject);
            msg.setContent(message, "text/plain");
            Transport.send(msg);
        } catch (MessagingException e) {
            LOG.error("Error trying to send mail!", e);
        }
    }

    private class SMTPAuthenticator extends Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(smtpUser, smtpPassword);
        }
    }
}
