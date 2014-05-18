package de.stekoe.idss.page.project.criterion.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.CriterionGroup;


public class CriterionGroupElementPanel extends Panel {

    private final CriterionGroup criterionGroup;

    public CriterionGroupElementPanel(String wicketId, CriterionGroup criterionGroup) {
        super(wicketId);
        this.criterionGroup = criterionGroup;

        add(new Label("group.name", criterionGroup.getName()));
        add(new CriterionPageElementListView("group.items", criterionGroup.getCriterions(), true));
    }

}
