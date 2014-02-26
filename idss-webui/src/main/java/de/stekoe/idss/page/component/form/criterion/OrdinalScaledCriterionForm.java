package de.stekoe.idss.page.component.form.criterion;

import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.service.CriterionService;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class OrdinalScaledCriterionForm extends Panel {

    @SpringBean
    private CriterionService itsCriterionService;

    public OrdinalScaledCriterionForm(final String aId, final String aCriterionId) {
        super(aId);

        LoadableDetachableModel<SingleScaledCriterion> model = new LoadableDetachableModel<SingleScaledCriterion>() {
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

        final Form<SingleScaledCriterion> form = new Form<SingleScaledCriterion>("ordinalScaledCriterionForm", new CompoundPropertyModel<SingleScaledCriterion>(model)) {
            @Override
            protected void onSubmit() {
                onSave(getModel());
            }
        };
        add(form);

        final TextField<String> nameTextField = new TextField<String>("name");
        form.add(nameTextField);

        final TextField<String> descriptionTextField = new TextField<String>("description");
        form.add(descriptionTextField);
        descriptionTextField.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));

        final CheckBox allowNoChoiceCheckBox = new CheckBox("allowNoChoice");
        form.add(allowNoChoiceCheckBox);
    }

    public abstract void onSave(IModel<SingleScaledCriterion> aModel);
}
