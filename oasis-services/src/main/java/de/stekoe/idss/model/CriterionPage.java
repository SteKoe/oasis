package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import de.stekoe.idss.model.OrderableUtil.Direction;

@Entity
public class CriterionPage implements Serializable {
    private static final long serialVersionUID = 20141103925L;

    private String id = IDGenerator.createId();
    private String name;
    private List<PageElement> pageElements = new ArrayList<PageElement>();
    private Project project;
    private int ordering = -1;

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @OneToMany(mappedBy="criterionPage", targetEntity = PageElement.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    public List<PageElement> getPageElements() {
        if(pageElements == null) {
            return new ArrayList<PageElement>();
        }
        return pageElements;
    }
    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }

    @Transient
    public boolean move(PageElement pageElement, Direction direction) {
        List<PageElement> oldList = pageElements;
        List<PageElement> reorderedList = OrderableUtil.<PageElement>move(pageElements, pageElement, direction);
        setPageElements(reorderedList);
        return !oldList.equals(reorderedList);
    }

    @ManyToOne(targetEntity = Project.class)
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    @Basic
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public int getOrdering() {
        return ordering;
    }
    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof PageElement)) return false;

        CriterionPage that  = (CriterionPage) other;
        return new EqualsBuilder()
            .appendSuper(super.equals(other))
            .append(getId(), that.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
