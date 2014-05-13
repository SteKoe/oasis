package de.stekoe.idss.page.project.criterion.referencecatalog;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.CriterionGroup;

public abstract class CriterionGroupForm extends Panel {

    public CriterionGroupForm(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<CriterionGroup> form = new Form<CriterionGroup>("form", (IModel<CriterionGroup>) getDefaultModel()) {
            @Override
            protected void onSubmit() {
                onSaveCriterionGroup(getModel());
            }
        };

        add(form);
    }

    public abstract void onSaveCriterionGroup(IModel<CriterionGroup> iModel);

}
