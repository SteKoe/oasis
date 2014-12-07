package de.stekoe.idss.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Company implements Serializable, Identifyable<String>, NamedElement {
    private static final long serialVersionUID = 201404131428L;

    private String id;
    private String name;
    private List<CompanyRole> roles = new ArrayList<CompanyRole>();
    private List<Address> addresses = new ArrayList<Address>();
    private List<Employee> employees = new ArrayList<Employee>();

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = CompanyRole.class, cascade = CascadeType.ALL)
    public List<CompanyRole> getRoles() {
        return (roles == null ? Collections.EMPTY_LIST : roles);
    }

    public void setRoles(List<CompanyRole> roles) {
        this.roles = roles;
    }

    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @OneToMany(targetEntity = Employee.class, cascade = CascadeType.ALL)
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Transient
    public Employee getEmployee(User user) {
        for (Employee e : getEmployees()) {
            if (e.getUser().getUsername().equals(user.getUsername())) {
                return e;
            }
            if (e.getUser().getEmail().equals(user.getEmail())) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Company)) return false;

        Company that = (Company) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
