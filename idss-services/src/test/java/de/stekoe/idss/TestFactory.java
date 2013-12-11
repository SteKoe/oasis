package de.stekoe.idss;

import de.stekoe.idss.model.User;

public class TestFactory {

    public static User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(username.toLowerCase().replace(" ", "")+"@example.com");
        user.setPassword(password);
        return user;
    }

    public static User createUser(String username) {
        return createUser(username, "secret");
    }
}
