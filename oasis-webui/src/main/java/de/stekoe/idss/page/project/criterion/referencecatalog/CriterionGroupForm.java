package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.tinymce.TinyMceBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.ReferenceCriterionService;
import de.stekoe.idss.session.WebSession;

public abstract class CriterionGroupForm extends Panel {

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    ReferenceCriterionService referenceCriterionService;

    private final String criterionGroupId;
    private final CriterionGroupModel criterionGroupModel;
    private ListMultipleChoice<Criterion> listChoice;

    public CriterionGroupForm(String wicketId, String criterionGroupId) {
        super(wicketId);
        this.criterionGroupId = criterionGroupId;
        criterionGroupModel = new CriterionGroupModel(criterionGroupId);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<CriterionGroup> form = new Form<CriterionGroup>("form", new CompoundPropertyModel<CriterionGroup>(criterionGroupModel)) {
            @Override
            protected void onSubmit() {
                onSaveCriterionGroup(getModel());
            }
        };

        final TextField<String> nameTextField = new TextField<String>("name");
        nameTextField.add(new PropertyValidator<String>());
        form.add(new FormGroup("name.group").add(nameTextField));

        final TextField<String> descriptionTextField = new TextField<String>("description");
        descriptionTextField.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        descriptionTextField.add(new PropertyValidator<String>());
        form.add(new FormGroup("description.group").add(descriptionTextField));

        listChoice = new ListMultipleChoice<Criterion>("criterions", referenceCriterionService.findAll(), new IChoiceRenderer<Criterion>() {
            @Override
            public Object getDisplayValue(Criterion object) {
                return object.getName();
            }

            @Override
            public String getIdValue(Criterion object, int index) {
                return object.getId();
            }
        });
        form.add(new FormGroup("criterions.group").add(listChoice));

        add(form);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forUrl("vendors/select2/select2.min.js"));
        response.render(JavaScriptHeaderItem.forUrl("vendors/select2/select2_locale_" + WebSession.get().getLocale().getLanguage() + ".js"));
        response.render(CssContentHeaderItem.forUrl("vendors/select2/select2.css"));
        response.render(JavaScriptHeaderItem.forScript("$(document).ready(function() { $(\".select2\").select2(); });", null));
    }

    protected void disableCriterionSelectionList() {
        listChoice.setVisible(false);
    }

    public abstract void onSaveCriterionGroup(IModel<CriterionGroup> iModel);

    class CriterionGroupModel extends Model<CriterionGroup> {
        private CriterionGroup modelObject;
        private final String id;

        public CriterionGroupModel(String id) {
            this.id = id;
        }

        @Override
        public CriterionGroup getObject() {
            if(modelObject != null) {
                return modelObject;
            }

            if(id == null) {
                modelObject = new CriterionGroup();
                return modelObject;
            } else {
                modelObject = criterionGroupService.findOne(id);
                return modelObject;
            }
        }
    }
}
