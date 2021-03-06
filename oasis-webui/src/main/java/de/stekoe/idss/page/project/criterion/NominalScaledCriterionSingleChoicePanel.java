package de.stekoe.idss.page.project.criterion;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;

public class NominalScaledCriterionSingleChoicePanel extends NominalScaledCriterionPanel {
    public NominalScaledCriterionSingleChoicePanel(String wicketId, NominalScaledCriterion criterion) {
        super(wicketId, criterion);
    }

    @Override
    void renderChoices() {
        List<NominalValue> availableValues = getPageElement().getValues();
        PropertyModel<NominalValue> selectedValues = new PropertyModel<NominalValue>(getModel(), "measurementValues.0");

        final RadioGroup radioGroup = new RadioGroup("radiogroup", selectedValues);
        add(radioGroup);

        ListView<NominalValue> listView = new ListView<NominalValue>("choices", availableValues) {
            @Override
            protected void populateItem(ListItem<NominalValue> item) {
                Radio radio = new Radio("radio", item.getModel());
                item.add(radio);
                item.add(new Label("label", item.getModelObject().getValue()));
            }
        };
        listView.setReuseItems(true);
        radioGroup.add(listView);
    }

}
