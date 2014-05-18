package de.stekoe.idss.page.project.criterion.page;

import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.SingleScaledCriterion;

class CriterionPageElementListView extends ListView<PageElement> {

    private static final String WICKET_ID = "page.list.item";
    private boolean isInGroup = false;

    public CriterionPageElementListView(String id, List<? extends PageElement> list) {
        super(id, list);
    }

    public CriterionPageElementListView(String id, List<? extends PageElement> list, boolean isInGroup) {
        this(id, list);
        this.isInGroup  = isInGroup;
    }


    @Override
    protected void populateItem(ListItem<PageElement> item) {
        final PageElement criterionPageElement = item.getModelObject();

        if (criterionPageElement instanceof Criterion) {
            Criterion crit = (Criterion)criterionPageElement;
            Set<CriterionGroup> criterionGroups = crit.getCriterionGroups();
            if(criterionGroups.size() > 0 && !isInGroup) {
                Label label = new Label(WICKET_ID,"");
                item.add(label);
                label.setVisible(false);
            } else if (criterionPageElement instanceof SingleScaledCriterion) {
                SingleScaledCriterion ssc = (SingleScaledCriterion) criterionPageElement;
                item.add(new SingleScaledCriterionElement(WICKET_ID, ssc));
            }
        } else if(criterionPageElement instanceof CriterionGroup) {
            Label label = new Label(WICKET_ID,"");
            item.add(new CriterionGroupElementPanel(WICKET_ID, (CriterionGroup)item.getModelObject()));
        }
    }
}