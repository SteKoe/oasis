package de.stekoe.idss.mailer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MailerTest {

    private Mailer mailer;

    @Before
    public void setUp() throws Exception {
        mailer = new Mailer();
    }

    @Test
    @Ignore
    public void test() {
        mailer.sendMail("stekoe2000@yahoo.de", "Testmail", "Testmessage");
    }

}
