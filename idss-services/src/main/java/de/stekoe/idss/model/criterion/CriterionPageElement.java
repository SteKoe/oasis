package de.stekoe.idss.model.criterion;

import de.stekoe.idss.IDGenerator;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CriterionPageElement {
    private String id = IDGenerator.createId();
    private int ordering;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }
}
