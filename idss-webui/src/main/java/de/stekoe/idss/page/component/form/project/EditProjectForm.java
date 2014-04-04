/*
 * Copyright 2014 Stephan Köninger
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

package de.stekoe.idss.page.component.form.project;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

public class EditProjectForm extends ProjectForm {

    @SpringBean
    private ProjectService projectService;
    @SpringBean
    private ProjectRoleService projectRoleService;

    public EditProjectForm(String id, String projectId) {
        super(id, projectId);
    }

    @Override
    public void onSave(IModel<Project> model) {
        projectService.save(model.getObject());
        WebSession.get().success("Projekt wurde bearbeitet!");
        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
