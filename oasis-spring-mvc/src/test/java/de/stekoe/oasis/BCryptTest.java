package de.stekoe.oasis;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    @Test
    public void testName() throws Exception {
        String password = BCrypt.hashpw("password", BCrypt.gensalt());
        System.out.println(password);
    }
}
