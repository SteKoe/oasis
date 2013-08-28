package de.stekoe.idss;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    @Test
    public void testName() throws Exception {
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        System.out.println(hashedPassword);
    }
}
