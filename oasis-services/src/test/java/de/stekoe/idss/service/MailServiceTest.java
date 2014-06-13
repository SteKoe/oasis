package de.stekoe.idss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.AbstractBaseTest;

public class MailServiceTest extends AbstractBaseTest {

    @Autowired
    private de.stekoe.idss.service.MailService mailer;

    @Test
    public void sendMail() throws Exception {
        mailer.sendMail("mail@example.com", "Test Mail", "Test Body");
    }
}
