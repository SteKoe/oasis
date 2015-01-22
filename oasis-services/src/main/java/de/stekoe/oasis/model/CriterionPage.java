package de.stekoe.oasis.model;

import de.stekoe.oasis.model.OrderableUtil.Direction;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CriterionPage implements Serializable {
    private static final long serialVersionUID = 20141103925L;

    private String id;
    private String name;
    private List<PageElement> pageElements = new ArrayList<>();
    private Project project;
    private int ordering = -1;

    public CriterionPage() {
    }

    public CriterionPage(CriterionPageDescriptor criterionPageDescriptor) {
        this.name = criterionPageDescriptor.getName();
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

    @OneToMany(mappedBy = "criterionPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ordering")
    public List<PageElement> getPageElements() {
        return pageElements;
    }
    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
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

    @Transient
    public boolean move(PageElement pageElement, Direction direction) {
        List<PageElement> oldList = pageElements;
        List<PageElement> reorderedList = OrderableUtil.move(pageElements, pageElement, direction);
        setPageElements(reorderedList);
        return !oldList.equals(reorderedList);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof PageElement)) return false;

        CriterionPage that = (CriterionPage) other;
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
