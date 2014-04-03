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

package de.stekoe.idss.page.project;

import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.page.component.form.project.EditProjectForm;
import de.stekoe.idss.service.ProjectService;

import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectEditPage extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    private static final Logger LOG = Logger.getLogger(ProjectEditPage.class);

    public ProjectEditPage(PageParameters params) {
        super(params);

        projectService.isAuthorized(getUser().getId(), getProjectId(), PermissionType.UPDATE);

        add(new EditProjectForm("form.project.edit", getProjectId()));
    }
}
