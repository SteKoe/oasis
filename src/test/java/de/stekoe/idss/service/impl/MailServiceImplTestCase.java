package de.stekoe.idss.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.stekoe.idss.service.IMailService;

/**
 * Test to check wether email server configuration works.
 *
 * <p>By default it should be turned off since it always
 * tries to send an email when run
 *
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Ignore
@ContextConfiguration(locations = { "classpath:/spring/BeanLocations.xml","classpath:/spring/TestBeanLocations.xml" })
public class MailServiceImplTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IMailService mailer;

    @Test
    public void sendMail() throws Exception {
        mailer.sendMail("mail@stekoe.de", "IDSS Test Mail", "IDSS Test Body");
    }
}
