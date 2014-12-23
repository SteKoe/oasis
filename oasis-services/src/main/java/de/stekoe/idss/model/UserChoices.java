package de.stekoe.idss.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class UserChoices implements Serializable {
    private String id;
    private User user;
    private Project project;
    private Map<String, UserChoice> userChoices = new HashMap<>();

    public UserChoices() {
        // NOP
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Returns a map having the Criterion ID as key and selected Choices as value.
     * @return Map<Criterion Id, Selected Values>
     */
    @OneToMany(mappedBy = "userChoices", cascade = CascadeType.ALL, orphanRemoval = true)
    public Map<String, UserChoice> getUserChoices() {
        return userChoices;
    }
    public void setUserChoices(Map<String, UserChoice> userChoices) {
        this.userChoices = userChoices;
    }
}