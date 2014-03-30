/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "SystemRole")
public class SystemRole implements Serializable {

    private static final long serialVersionUID = 101403011955L;

    public static final transient String USER = "USER";
    public static final transient String ADMIN = "ADMIN";

    private SystemRoleId id = new SystemRoleId();
    private String name;
    private Set<User> users = new HashSet<User>(0);

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "system_role_id"))
    public SystemRoleId getId() {
        return this.id;
    }

    public void setId(SystemRoleId id) {
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

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "User_SystemRole", joinColumns = { @JoinColumn(name = "system_role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
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