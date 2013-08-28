package de.stekoe.idss.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.stekoe.idss.service.IMailService;

@ContextConfiguration(locations = { "classpath:/spring/BeanLocations.xml","classpath:/spring/TestBeanLocations.xml" })
public class MailServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IMailService mailer;

    @Test
    public void sendMail() throws Exception {
        mailer.sendMail("mail@stekoe.de", "IDSS Test Mail", "IDSS Test Body");
    }
}
