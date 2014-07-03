package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PageElement implements NamedElement, Serializable {
    private static final long serialVersionUID = 20141103926L;

    private String id = IDGenerator.createId();
    private CriterionPage criterionPage;
    private String name;
    private String description;
    private boolean referenceType = false;
    private String originId;

    public PageElement() {
    }

    /**
     * Copy constructor.
     * Creates a copy of the given PageElement. The resulting new PageElement object will have an pointer to the original
     * (copied) PageElement identified by originId:
     *
     * PageElement              Copy of PageElement
     *   id          &gt;-------     originId
     *
     * @param pageElement The PageElement to copy
     */
    public PageElement(PageElement pageElement) {
        this.criterionPage = pageElement.getCriterionPage();
        this.name = pageElement.getName();
        this.description = pageElement.getDescription();
        this.originId = pageElement.getId();
    }

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = CriterionPage.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderColumn(name = "ordering")
    public CriterionPage getCriterionPage() {
        return criterionPage;
    }
    public void setCriterionPage(CriterionPage criterionPage) {
        this.criterionPage = criterionPage;

        if(criterionPage != null) {
            try {
                if(!criterionPage.getPageElements().contains(this)) {
                    criterionPage.getPageElements().add(this);
                }
            } catch(Exception e) {

            }
        }
    }

    @Override
    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isReferenceType() {
        return referenceType;
    }
    public void setReferenceType(boolean referenceType) {
        this.referenceType = referenceType;
    }

    @Basic
    public String getOriginId() {
        return originId;
    }
    public void setOriginId(String originId) {
        this.originId = originId;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof PageElement)) return false;

        PageElement that  = (PageElement) other;
        return new EqualsBuilder()
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
