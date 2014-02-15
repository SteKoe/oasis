package de.stekoe.idss.model.criterion;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.project.Project;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@DynamicUpdate
public class CriterionPage implements Serializable {
    private String id = IDGenerator.createId();
    private int ordering;
    private List<CriterionPageElement> pageElements = new ArrayList<CriterionPageElement>();
    private Project project;

    @Id
    @Column(name = "criterion_page_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @OrderBy(value = "ordering")
    @OneToMany(targetEntity = CriterionPageElement.class)
    public List<CriterionPageElement> getPageElements() {
        return pageElements;
    }

    public void setPageElements(List<CriterionPageElement> pageElements) {
        this.pageElements = pageElements;
    }

    @ManyToOne(targetEntity = Project.class, optional = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        CriterionPage rhs = (CriterionPage) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).hashCode();
    }
}
