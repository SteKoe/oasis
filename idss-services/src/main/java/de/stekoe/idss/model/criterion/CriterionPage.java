package de.stekoe.idss.model.criterion;

import de.stekoe.idss.IDGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class CriterionPage implements Serializable {
    private String id = IDGenerator.createId();
    private int ordering;
    private List<CriterionPageElement> pageElements = new ArrayList<CriterionPageElement>();

    @Id
    @Column(name = "criterion_page_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @OrderBy(value = "order")
    @OneToMany(targetEntity = CriterionPageElement.class)
    public List<CriterionPageElement> getPageElements() {
        return pageElements;
    }

    public void setPageElements(List<CriterionPageElement> pageElements) {
        this.pageElements = pageElements;
    }
}
