package de.stekoe.idss.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "SystemRole")
public class SystemRole implements Serializable {

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private String id;
    private String name;
    private Collection<User> users = new HashSet<User>(0);

    @Id
    @Column(name = "system_role_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }

    @ManyToMany(mappedBy = "roles")
    public Collection<User> getUsers() {
        return this.users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != getClass()) { return false; }
        SystemRole that = (SystemRole) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getName(), that.getName())
                .append(getUsers(), that.getUsers())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .append(getUsers())
                .hashCode();
    }
}