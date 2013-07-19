package de.stekoe.idss.exception;

public class UserAlreadyExistsException extends Exception {
    private static final long serialVersionUID = -8078241715847776796L;

    public UserAlreadyExistsException() {
        super(
                "User with the given username already exists in database. Usernames must be unique!");
    }
}
