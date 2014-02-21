package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.enums.CriterionType;
import de.stekoe.idss.model.scale.value.MeasurementValue;
import de.stekoe.idss.model.scale.value.OrdinalValue;
import de.stekoe.idss.page.project.ProjectPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateCriterionPage extends ProjectPage {
    public CreateCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        final String criterionTypeCandidate = pageParameters.get("type").toString();

        CriterionType criterionType = null;
        try {
            criterionType = CriterionType.valueOf(criterionTypeCandidate);
        } catch(IllegalArgumentException e) {
            getSession().error("Desired type does not exist.");
            setResponsePage(SelectCriterionPage.class, getPageParameters());
            return;
        }

        final Form form = new Form("form");
        add(form);


        List<MeasurementValue> values = new ArrayList<MeasurementValue>();
        values.add(new OrdinalValue(1, "sehr gut"));
        values.add(new OrdinalValue(2, "gut"));
        values.add(new OrdinalValue(3, "befriedigend"));
        values.add(new OrdinalValue(4, "ausreichend"));
        values.add(new OrdinalValue(5, "mangelhaft"));
        values.add(new OrdinalValue(6, "ungenügend"));

        final ListView<MeasurementValue> measurementValueListView = new ListView<MeasurementValue>("table.entries", values) {
            @Override
            protected void populateItem(ListItem<MeasurementValue> item) {
                final MeasurementValue measurementValue = item.getModelObject();

                item.add(new Label("table.entry.value", measurementValue.getValue()));
            }
        };
        form.add(measurementValueListView);

        final WebMarkupContainer emptyListIndicator = new WebMarkupContainer("table.empty");
        form.add(emptyListIndicator);
        emptyListIndicator.setVisible(measurementValueListView.getModelObject().size() == 0);
    }
}
