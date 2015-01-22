package de.stekoe.oasis.model;

public class CriterionGroupDescriptor {

    private String id;
    private String name;
    private String description;
    private String originId;
    private String criterionPageId;

    public CriterionGroupDescriptor(CriterionGroup criterionGroup) {
        setId(criterionGroup.getId());
        setName(criterionGroup.getName());
        setDescription(criterionGroup.getDescription());
        setOriginalId(criterionGroup.getOriginId());
        setCriterionPageId(criterionGroup.getCriterionPage());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getOriginId() {
        return originId;
    }

    public void setOriginalId(String originalId) {
        this.originId = originalId;
    }

    private void setCriterionPageId(CriterionPage criterionPage) {
        if (criterionPage != null) {
            setCriterionPageId(criterionPage.getId());
        }
    }

    public String getCriterionPageId() {
        return criterionPageId;
    }

    public void setCriterionPageId(String project) {
        this.criterionPageId = project;
    }
}
