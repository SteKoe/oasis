package de.stekoe.idss.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PageElement implements NamedElement, Serializable {
    private static final long serialVersionUID = 20141205;

    private String id;
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
     * <p/>
     * PageElement              Copy of PageElement
     * id          &gt;-------     originId
     *
     * @param pageElement The PageElement to copy
     */
    public PageElement(PageElement pageElement) {
        this.name = pageElement.getName();
        this.description = pageElement.getDescription();
        this.originId = pageElement.getId();
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
    public CriterionPage getCriterionPage() {
        return criterionPage;
    }
    public void setCriterionPage(CriterionPage criterionPage) {
        this.criterionPage = criterionPage;
    }

    @Override
    @NotNull
    @Size(min = 2, max = 255)
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

    @Column
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
        if (this == other) return true;
        if (!(other instanceof PageElement)) return false;

        PageElement that = (PageElement) other;
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
