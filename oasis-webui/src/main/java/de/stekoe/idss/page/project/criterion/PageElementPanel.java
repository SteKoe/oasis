package de.stekoe.idss.page.project.criterion;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.UserChoice;

public abstract class PageElementPanel<T extends PageElement> extends Panel {
    private static final String CONTENT_ID = "element.content";
    private final T pageElement;
    private IModel model;

    public PageElementPanel(String id, T pageElement) {
        super(id);
        this.pageElement = pageElement;

        addName();
        addDescription();
    }

    private void addDescription() {
        String elementDescription = this.getPageElement().getDescription();
        Label elementDescriptionLabel = new Label("element.description", elementDescription);
        elementDescriptionLabel.setEscapeModelStrings(false);
        elementDescriptionLabel.setVisible(!StringUtils.isBlank(elementDescription));
        add(elementDescriptionLabel);
    }

    private void addName() {
        String elementName = this.getPageElement().getName();
        Label elementNameLabel = new Label("element.name", getPageElement().getName());
        elementNameLabel.setVisible(!StringUtils.isBlank(elementName));
        add(elementNameLabel);
    }

    public T getPageElement() {
        return pageElement;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof PageElementPanel)) return false;

        PageElementPanel that  = (PageElementPanel) other;
        return new EqualsBuilder()
            .append(pageElement.getId(), that.pageElement.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(pageElement.getId())
            .toHashCode();
    }

    public abstract IModel<UserChoice> getModel();
}
