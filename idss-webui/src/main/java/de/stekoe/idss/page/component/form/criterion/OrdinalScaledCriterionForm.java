package de.stekoe.idss.page.component.form.criterion;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class OrdinalScaledCriterionForm extends Panel {

    @SpringBean
    private CriterionService itsCriterionService;
    private LoadableDetachableModel<SingleScaledCriterion> itsCriterionModel;

    public OrdinalScaledCriterionForm(final String aId, final String aCriterionId) {
        super(aId);
        itsCriterionModel = new LoadableDetachableModel<SingleScaledCriterion>() {
            @Override
            protected SingleScaledCriterion load() {
                if (aCriterionId == null) {
                    SingleScaledCriterion criterion = new SingleScaledCriterion();
                    criterion.setScale(new OrdinalScale());
                    return criterion;
                } else {
                    return (SingleScaledCriterion) itsCriterionService.findById(aCriterionId);
                }
            }
        };

        scaleForm();
        valueForm();
    }

    private void scaleForm() {


        final Form<SingleScaledCriterion> scaleForm = new Form<SingleScaledCriterion>("ordinalScaledCriterionForm", new CompoundPropertyModel<SingleScaledCriterion>(itsCriterionModel)) {
            @Override
            protected void onSubmit() {
                onSave(getModel());
            }
        };
        add(scaleForm);


        final TextField<String> nameTextField = new TextField<String>("name");
        nameTextField.setLabel(Model.of(getString("label.name")));
        nameTextField.add(new PropertyValidator<String>());
        scaleForm.add(new FormGroup("name.group").add(nameTextField));

        final TextField<String> descriptionTextField = new TextField<String>("description");
        nameTextField.setLabel(Model.of(getString("label.description")));
        descriptionTextField.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        descriptionTextField.add(new PropertyValidator<String>());
        scaleForm.add(new FormGroup("description.group").add(descriptionTextField));

        final CheckBox allowNoChoiceCheckBox = new CheckBox("allowNoChoice");
        scaleForm.add(allowNoChoiceCheckBox);


        scaleForm.add(new MarkRequiredFieldsBehavior());

        add(new SubmitLink("submit.ordinalScaledCriterionForm", scaleForm));
    }

    private void valueForm() {
        final OrdinalValue ordinalValue = new OrdinalValue();
        final Form<OrdinalValue> valueForm = new Form<OrdinalValue>("valueForm", new CompoundPropertyModel<OrdinalValue>(ordinalValue)) {
            @Override
            protected void onSubmit() {
                final SingleScaledCriterion criterion = itsCriterionModel.getObject();
                criterion.getScale().getValues().add(getModelObject());
                itsCriterionService.save(criterion);
                setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters());
            }
        };
        add(valueForm);

        final RequiredTextField<String> valueTextField = new RequiredTextField<String>("value");
        valueForm.add(new FormGroup("value.group").add(valueTextField));

        final ListView<OrdinalValue> valueList = new ListView<OrdinalValue>("value.list", itsCriterionModel.getObject().getScale().getValues()) {
            @Override
            protected void populateItem(ListItem<OrdinalValue> item) {
                item.add(new Label("value.list.label", item.getModelObject().getValue()));
            }
        };
        valueForm.add(valueList);

        final WebMarkupContainer emptyListIndicator = new WebMarkupContainer("value.list.empty");
        valueForm.add(emptyListIndicator);
        emptyListIndicator.setVisible(valueList.getList().isEmpty());
    }

    public abstract void onSave(IModel<SingleScaledCriterion> aModel);
}
