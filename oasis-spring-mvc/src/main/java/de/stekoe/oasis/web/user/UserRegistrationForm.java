package de.stekoe.oasis.web.user;

import de.stekoe.oasis.model.CompanyDescriptor;
import de.stekoe.oasis.web.validator.FieldMatch;
import de.stekoe.oasis.web.validator.FieldXor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword")
@FieldXor(first = "company.name", second = "registryToken", message = "{FieldXor.registration}")
public class UserRegistrationForm {
    private String email;
    private String password;
    private String confirmPassword;
    private String registryToken;
    private CompanyDescriptor company;

    @NotNull
    @Size(min = 5)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

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

    public CompanyDescriptor getCompany() {
        return company;
    }

    public void setCompany(CompanyDescriptor company) {
        this.company = company;
    }

    public String getRegistryToken() {
        return registryToken;
    }

    public void setRegistryToken(String registryToken) {
        this.registryToken = registryToken;
    }
}
