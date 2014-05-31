package de.stekoe.idss.page.project.criterion;

import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;

public class NominalScaledCriterionMultipleChoicePanel extends NominalScaledCriterionPanel {
    public NominalScaledCriterionMultipleChoicePanel(String wicketId, NominalScaledCriterion criterion) {
        super(wicketId, criterion);
    }

    @Override
    void renderChoices() {
        List<NominalValue> availableValues = getPageElement().getValues();
        PropertyModel<List<NominalValue>> selectedValues = new PropertyModel<List<NominalValue>>(getModel(), "measurementValues");

        CheckBoxMultipleChoice<NominalValue> checkboxes = new CheckBoxMultipleChoice<NominalValue>("checkboxes", selectedValues, availableValues, new IChoiceRenderer<NominalValue>() {
            @Override
            public Object getDisplayValue(NominalValue object) {
                return object.getValue();
            }

            @Override
            public String getIdValue(NominalValue object, int index) {
                return object.getId();
            }
        });
        checkboxes.setPrefix("<div class='checkbox choices'>");
        checkboxes.setSuffix("</div>");
        add(checkboxes);
    }

}
