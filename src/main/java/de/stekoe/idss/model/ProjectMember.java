package de.stekoe.idss.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "ProjectMember")
public class ProjectMember implements Serializable {

    private String id;
    private User user;
    private Set<ProjectRole> projectRoles;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ProjectRole.class)
    @JoinTable(name = "ProjectMemberToProjectRole")
    public Set<ProjectRole> getProjectRoles() {
        return this.projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }
}
