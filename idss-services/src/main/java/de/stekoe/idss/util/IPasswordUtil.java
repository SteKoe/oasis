package de.stekoe.idss.util;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface IPasswordUtil {
    String hashPassword(String cleartext);
    boolean checkPassword(String plaintext, String hash);
}
