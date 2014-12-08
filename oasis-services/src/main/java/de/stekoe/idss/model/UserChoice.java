package de.stekoe.idss.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserChoice implements Serializable {
    private static final long serialVersionUID = 20141208;

    private String id;
    private List<String> choices = new ArrayList<>();
    private UserChoices userChoices;

    public UserChoice() {
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

    @ElementCollection
    public List<String> getChoices() {
        return choices;
    }
    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    @ManyToOne
    public UserChoices getUserChoices() {
        return userChoices;
    }
    public void setUserChoices(UserChoices userChoices) {
        this.userChoices = userChoices;
    }
}
