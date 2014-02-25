package de.stekoe.idss.page.component.criterion;

import de.stekoe.idss.model.criterion.MultiScaledCriterion;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.Scale;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class MultiScaledCriterionPanel extends Panel {

    public static final String SUBCRITERION_PANEL_ID = "subcriterion.panel";

    public MultiScaledCriterionPanel(String id, final MultiScaledCriterion multiScaledCriterion) {
        super(id);

        final Label criterionName = new Label("criterion.name", multiScaledCriterion.getName());
        add(criterionName);

        final Label criterionDescription = new Label("criterion.description", multiScaledCriterion.getDescription()) {
            @Override
            public boolean isVisible() {
                return !StringUtils.isEmpty(multiScaledCriterion.getDescription());
            }
        };
        add(criterionDescription);

        final ListView<SingleScaledCriterion> subCriterions = new ListView<SingleScaledCriterion>("subcriteria", multiScaledCriterion.getSubCriterions()) {
            @Override
            protected void populateItem(ListItem<SingleScaledCriterion> item) {
                final SingleScaledCriterion ssc = item.getModelObject();

                final Scale scale = ssc.getScale();
                if(scale instanceof OrdinalScale) {
                    item.add(getSubCriterionPanelForOrdinalScale(ssc));
                } else {
                    item.add(new Label(SUBCRITERION_PANEL_ID));
                }
            }
        };
        add(subCriterions);
    }

    private Panel getSubCriterionPanelForOrdinalScale(SingleScaledCriterion ssc) {
        final OrdinalScalePanel components = new OrdinalScalePanel(SUBCRITERION_PANEL_ID, ssc);
        components.setHorizontal(true);
        return components;
    }
}
