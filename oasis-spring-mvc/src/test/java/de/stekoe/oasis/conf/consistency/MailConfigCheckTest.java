package de.stekoe.oasis.conf.consistency;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MailConfigCheckTest {

    @Test
    public void checkConnect() throws Exception {
        MailConfigCheckMock checker = new MailConfigCheckMock();
        assertThat(checker.check(), is(true));
    }

    class MailConfigCheckMock extends MailConfigChecker {
        @Override
        String getHost() {
            return "localhost";
        }

        @Override
        int getPort() {
            return 1234;
        }

        @Override
        boolean isSsl() {
            return false;
        }
    }
}