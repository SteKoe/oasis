package de.stekoe.idss.page.project.criterion;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;

public class OrdinalScaledCriterionSingleChoicePanel extends OrdinalScaledCriterionPanel {
    public OrdinalScaledCriterionSingleChoicePanel(String wicketId, OrdinalScaledCriterion criterion) {
        super(wicketId, criterion);
    }

    @Override
    void renderChoices() {
        List<OrdinalValue> availableValues = getPageElement().getValues();
        PropertyModel<OrdinalValue> selectedValues = new PropertyModel<OrdinalValue>(getModel(), "measurementValues.0");

        final RadioGroup radioGroup = new RadioGroup("radiogroup", selectedValues);
        add(radioGroup);

        ListView<OrdinalValue> listView = new ListView<OrdinalValue>("choices", availableValues) {
            @Override
            protected void populateItem(ListItem<OrdinalValue> item) {
                Radio radio = new Radio("radio", item.getModel());
                item.add(radio);
                item.add(new Label("label", item.getModelObject().getValue()));
            }
        };
        listView.setReuseItems(true);
        radioGroup.add(listView);
    }

}
