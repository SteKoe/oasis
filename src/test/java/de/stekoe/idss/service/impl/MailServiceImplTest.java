package de.stekoe.idss.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import de.stekoe.idss.service.MailService;

@Ignore
public class MailServiceImplTest {

    private FileSystemXmlApplicationContext context;
    private MailService mailer;

    @Before
    public void setUp() {
        context = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
        mailer = (MailService) context.getBean("mailService");
    }

    @Test
    public void sendMail() throws Exception {
        mailer.sendMail("mail@stekoe.de", "Test Subject", "Testing body");
    }
}
