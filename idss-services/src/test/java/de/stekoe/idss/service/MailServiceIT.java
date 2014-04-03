package de.stekoe.idss.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.AbstractBaseTest;

/**
 * Test to check wether email server configuration works.
 * <p/>
 * <p>By default it should be turned off since it always
 * tries to send an email when run
 *
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Ignore
public class MailServiceIT extends AbstractBaseTest {

    @Autowired
    private de.stekoe.idss.service.MailService mailer;

    @Test
    public void sendMail() throws Exception {
        mailer.sendMail("mail@stekoe.de", "IDSS Test Mail", "IDSS Test Body");
    }
}
