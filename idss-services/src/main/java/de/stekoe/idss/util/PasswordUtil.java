package de.stekoe.idss.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class PasswordUtil implements IPasswordUtil {
    @Override
    public String hashPassword(String cleartext) {
        return BCrypt.hashpw(cleartext, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plaintext, String hash) {
        return BCrypt.checkpw(plaintext, hash);
    }
}
