package de.stekoe.idss.page.project.criterion;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.CriterionGroup;


public class CriterionGroupSurveyElementPanel extends Panel {
    public CriterionGroupSurveyElementPanel(String wicketId, CriterionGroup criterionGroup) {
        super(wicketId);

        add(new Label("group.title", criterionGroup.getName()));

        Label groupDescription = new Label("group.description", criterionGroup.getDescription());
        add(groupDescription);
        groupDescription.setVisible(StringUtils.isNotBlank(criterionGroup.getDescription()));
        groupDescription.setEscapeModelStrings(false);
    }
}
