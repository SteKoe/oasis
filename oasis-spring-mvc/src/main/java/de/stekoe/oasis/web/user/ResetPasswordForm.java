package de.stekoe.oasis.web.user;

import de.stekoe.oasis.web.validator.FieldMatch;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword")
public class ResetPasswordForm {
    private String password;
    private String confirmPassword;

    @NotNull
    @Size(min = 7)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
