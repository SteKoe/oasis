package de.stekoe.oasis.conf.consistency;

import de.stekoe.oasis.conf.MailConfig;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class MailConfigChecker implements ConsistencyChecker {

    Logger logger = Logger.getLogger(MailConfigChecker.class);

    @Autowired
    MailConfig mailConfig;

    @Override
    public boolean check() {
        try {
            SMTPClient smtpClient;
            if (isSsl()) {
                smtpClient = getAuthenticatingSMTPClient();
            } else {
                smtpClient = new SMTPClient();
            }

            smtpClient.setDefaultTimeout(10 * 1000);
            smtpClient.connect(getHost(), getPort());
            smtpClient.helo(getHost());
            smtpClient.quit();
        } catch (Exception e) {
            logger.error("MailConfigChecker failed!", e);
            return false;
        }

        return true;
    }

    boolean isSsl() {
        return mailConfig.getSsl().equals("true");
    }

    int getPort() {
        return Integer.parseInt(mailConfig.getPort());
    }

    String getHost() {
        return mailConfig.getHost();
    }

    private SMTPClient getAuthenticatingSMTPClient() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        AuthenticatingSMTPClient smtpClient = new AuthenticatingSMTPClient("TLS", true);
        smtpClient.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, mailConfig.getUser(), mailConfig.getPassword());
        return smtpClient;
    }
}
