package de.stekoe.oasis.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Company implements Serializable, Identifyable<String>, NamedElement {
    private static final long serialVersionUID = 201404131428L;

    private String id;
    private String name;
    private String registrationToken = RandomStringUtils.randomAlphanumeric(7);

    private List<CompanyRole> roles = new ArrayList<CompanyRole>();
    private List<Address> addresses = new ArrayList<Address>();
    private List<Employee> employees = new ArrayList<Employee>();

    public Company() {
        // NOP
    }

    public Company(CompanyDescriptor companyDescriptor) {
        setName(companyDescriptor.getName());
    }

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

    @OneToMany(targetEntity = CompanyRole.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ordering")
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

    @Column(unique = true)
    public String getRegistrationToken() {
        return registrationToken;
    }
    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    @Transient
    public Employee getEmployee(User user) {
        for (Employee e : getEmployees()) {
            if (e.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
                return e;
            }
            if (e.getUser().getEmail().equalsIgnoreCase(user.getEmail())) {
                return e;
            }
        }
        return null;
    }

    @Transient
    public Employee getEmployee(String usernameOrEmail) {
        Optional<Employee> any = getEmployees().stream().map(emp -> (Employee) emp).filter(emp -> {
            return emp.getUser().getUsername().equalsIgnoreCase(usernameOrEmail) || emp.getUser().getEmail().equalsIgnoreCase(usernameOrEmail);
        }).findAny();

        if(any.isPresent()) {
            return any.get();
        }

        return null;
    }

    public List<Employee> getEmployeesByRole(CompanyRole companyRole) {
        return getEmployees().stream().filter(emp -> emp.getRole() != null && emp.getRole().equals(companyRole)).collect(Collectors.toList());
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
