package de.stekoe.idss.model.criterion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Criterion extends CriterionPageElement implements Serializable {
    private String name;
    private String description;
    private boolean allowNoChoice = false;

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isAllowNoChoice() {
        return allowNoChoice;
    }

    public void setAllowNoChoice(boolean aAllowNoChoice) {
        allowNoChoice = aAllowNoChoice;
    }
}
