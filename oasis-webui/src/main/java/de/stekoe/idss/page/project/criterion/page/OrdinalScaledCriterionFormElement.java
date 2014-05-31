package de.stekoe.idss.page.project.criterion.page;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.service.CriterionService;

public class OrdinalScaledCriterionFormElement extends Panel {

    @SpringBean
    private CriterionService criterionService;

    private final String criterionId;

    private final LoadableDetachableModel<OrdinalScaledCriterion> criterionModel = new LoadableDetachableModel<OrdinalScaledCriterion>() {
        @Override
        protected OrdinalScaledCriterion load() {
            return (OrdinalScaledCriterion) criterionService.findSingleScaledCriterionById(criterionId);
        }
    };

    public OrdinalScaledCriterionFormElement(String id, OrdinalScaledCriterion criterion) {
        super(id);
        criterionId = criterion.getId();

        addNameLabel();
        addDescriptionLabel();

        add(new DropDownChoice<OrdinalValue>("choices", criterionModel.getObject().getValues(), new IChoiceRenderer<OrdinalValue>() {
            @Override
            public Object getDisplayValue(OrdinalValue object) {
                return object.getValue();
            }

            @Override
            public String getIdValue(OrdinalValue object, int index) {
                return object.getId();
            }
        }));
    }

    private void addNameLabel() {
        Label name = new Label("name", criterionModel.getObject().getName());
        add(name);
    }

    private void addDescriptionLabel() {
        String description = criterionModel.getObject().getDescription();
        Label descriptionLabel = new Label("description", description);
        add(descriptionLabel);
        descriptionLabel.setEscapeModelStrings(false);
        if(StringUtils.isBlank(description)) {
            descriptionLabel.setVisible(false);
        }
    }
}
