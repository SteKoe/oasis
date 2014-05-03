package de.stekoe.idss.page.project.criterion;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.UserChoiceRepository;

public abstract class PageElementPanel<T extends PageElement> extends Panel {

    @Inject
    UserChoiceRepository userChoiceRepository;

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

    public abstract IModel<UserChoice> getModel();
}
