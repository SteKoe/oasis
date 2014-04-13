package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company implements Serializable {
    private static final long serialVersionUID = 201404131428L;

    private String id = IDGenerator.createId();
    private String name;
    private List<CompanyRole> roles = new ArrayList<CompanyRole>();
    private List<Address> addresses = new ArrayList<Address>();
    private List<Employee> employees = new ArrayList<Employee>();

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Basic
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = CompanyRole.class)
    public List<CompanyRole> getRoles() {
        return roles;
    }
    public void setRoles(List<CompanyRole> roles) {
        this.roles = roles;
    }

    @OneToMany(targetEntity = Address.class)
    public List<Address> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @OneToMany(targetEntity = Employee.class)
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
