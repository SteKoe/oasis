package de.stekoe.idss.model;

// Generated 26.08.2013 06:45:56 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Project implements java.io.Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable=false)
    private String projectName;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="ProjectLeader")
    private final Set<User> projectLeader = new HashSet<User>();

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinTable(name="ProjectMember")
    private Set<User> projectMember = new HashSet<User>();

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<User> getProjectMember() {
        return projectMember;
    }

    public void setProjectMember(Set<User> projectMember) {
        this.projectMember = projectMember;
    }

    public Set<User> getProjectLeader() {
        return projectMember;
    }

    public void setProjectLeader(Set<User> projectLeader) {
        this.projectMember = projectLeader;
    }
}
