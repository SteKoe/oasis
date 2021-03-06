package de.stekoe.idss.page.project.criterion;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;

/**
 * @author Stephan Koeninger
 */
public abstract class OrdinalScaledCriterionForm extends CriterionForm<OrdinalValue> {

    @SpringBean
    private CriterionPageService criterionPageService;

    @SpringBean
    private CriterionService criterionService;

    private Form<OrdinalValue> valueForm;

    public OrdinalScaledCriterionForm(final String aId, final String aCriterionId) {
        super(aId, aCriterionId);

        valueForm();
    }

    /**
     * Subform for just the values of the scale
     */
    private void valueForm() {
        valueForm = new Form<OrdinalValue>("valueForm", new CompoundPropertyModel<OrdinalValue>(new OrdinalValue())) {
            @Override
            protected void onSubmit() {
                final SingleScaledCriterion<OrdinalValue> criterion = getCriterionModel().getObject();

                final OrdinalValue value = getModel().getObject();
                value.setCriterion(criterion);
                criterion.getValues().add(value);

                getCriterionModel().setObject(criterion);

                onSaveCriterion(getCriterionModel());
            }
        };
        add(valueForm);

        final RequiredTextField<String> valueTextField = new RequiredTextField<String>("value");
        valueTextField.add(new Placeholder("Ausprägung..."));
        FormGroup valueGroup = new FormGroup("value.group");
        valueForm.add(valueGroup.add(valueTextField));

        final LoadableDetachableModel<List<OrdinalValue>> valueListModel = new LoadableDetachableModel<List<OrdinalValue>>() {
            @Override
            protected List<OrdinalValue> load() {
                return getCriterionModel().getObject().getValues();
            }
        };

        final ListView<OrdinalValue> valueList = new ListView<OrdinalValue>("value.list", valueListModel) {
            @Override
            protected void populateItem(final ListItem<OrdinalValue> item) {
                final OrdinalValue value = item.getModelObject();
                Label labelValue = new Label("value.list.value", value.getValue());
                item.add(labelValue);

                Form<OrdinalValue> valueFormEdit = new Form<OrdinalValue>("value.form.edit", new CompoundPropertyModel<OrdinalValue>(value)) {
                    @Override
                    protected void onSubmit() {
                        OrdinalValue editedOrdinalValue = getModel().getObject();
                        SingleScaledCriterion criterion = editedOrdinalValue.getCriterion();
                        criterionService.save(criterion);
                    }
                };
                item.add(valueFormEdit);

                TextField<String> textField = new TextField<String>("value.list.value.textfield", new PropertyModel<String>(value, "value"));
                valueFormEdit.add(textField);

                item.add(new Link("value.delete") {
                    @Override
                    public void onClick() {
                        getCriterionModel().getObject().getValues().remove(value);
                        criterionService.save(getCriterionModel().getObject());
                        setResponsePage(getPage());
                    }
                });

                item.add(new Link("value.move.up") {
                    @Override
                    public void onClick() {
                        moveValueUp(value);
                    }

                    @Override
                    public boolean isVisible() {
                        return item.getIndex() > 0 && getList().size() > 1;
                    }
                });

                item.add(new Link("value.move.down") {
                    @Override
                    public void onClick() {
                        moveValueDown(value);
                    }

                    @Override
                    public boolean isVisible() {
                        return item.getIndex() < (getList().size()-1) && getList().size() > 1;
                    }
                });
            }
        };
        add(valueList);

        final WebMarkupContainer emptyListIndicator = new WebMarkupContainer("value.list.empty");
        add(emptyListIndicator);
        emptyListIndicator.setVisible(valueList.getList().isEmpty());
    }

    @Override
    public LoadableDetachableModel<SingleScaledCriterion<OrdinalValue>> getCriterionModel() {
        return new LoadableDetachableModel<SingleScaledCriterion<OrdinalValue>>() {
            @Override
            protected SingleScaledCriterion<OrdinalValue> load() {
                if (getCriterionId() == null) {
                    OrdinalScaledCriterion criterion = new OrdinalScaledCriterion();
                    return criterion;
                } else {
                    return criterionService.findSingleScaledCriterionById(getCriterionId());
                }
            }
        };
    }
}
