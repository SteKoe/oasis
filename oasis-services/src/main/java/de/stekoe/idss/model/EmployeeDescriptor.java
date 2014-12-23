package de.stekoe.idss.model;

public class EmployeeDescriptor  {
    private static final long serialVersionUID = 201404132211L;

    private String id;
    private String role;
    private String email;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
