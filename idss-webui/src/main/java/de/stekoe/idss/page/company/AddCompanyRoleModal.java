/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.page.company;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.service.UserService;

public abstract class AddCompanyRoleModal extends Modal {

    @SpringBean
    private UserService userService;

    private final ProjectMember projectMember = new ProjectMember();

    private final CompanyRole companyRole = new CompanyRole();
    private final List<PermissionType> selectedPermissionTypes = new ArrayList<PermissionType>();

    public AddCompanyRoleModal(String id) {
        super(id);

        header(Model.of(getString("label.project.add.member")));

        final Form form = new Form("form.add.role") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                for(PermissionType permissionType : selectedPermissionTypes) {
                    companyRole.getPermissions().add(new Permission(PermissionObject.COMPANY, permissionType, null));
                }
                onSave(companyRole);
            }
        };
        add(form);

        TextField<String> roleNameField = new TextField<String>("role.name", new PropertyModel<String>(companyRole, "name"));
        form.add(roleNameField);


        final CheckBoxMultipleChoice<PermissionType> permissions = new CheckBoxMultipleChoice<PermissionType>("permissionTypes", new Model((Serializable) selectedPermissionTypes), new ArrayList(PermissionType.forCompany()), new PermissionTypeChoiceRenderer());
        permissions.setPrefix("<div class='checkbox'>");
        permissions.setSuffix("</div>");
        form.add(permissions);

        addButton(new ModalCloseButton(new ResourceModel("label.cancel")));

        final SubmitLink submitButton = new SubmitLink("button", form);
        addButton(submitButton);
        submitButton.setBody(Model.of(getString("label.add")));
        submitButton.add(new ButtonBehavior(Buttons.Type.Success));
    }

    public abstract void onSave(CompanyRole companyRole);

    private class PermissionTypeChoiceRenderer implements IChoiceRenderer<PermissionType> {
        @Override
        public Object getDisplayValue(PermissionType object) {
            return " " + MessageFormat.format(getString(object.getKey()), getString(PermissionObject.COMPANY.getKey()));
        }

        @Override
        public String getIdValue(PermissionType object, int index) {
            return object.getKey();
        }
    };
}
