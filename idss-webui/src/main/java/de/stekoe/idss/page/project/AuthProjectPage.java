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

import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class AuthProjectPage extends AuthUserPage {
    private static final Logger LOG = Logger.getLogger(AuthProjectPage.class);

    @SpringBean
    private ProjectService projectService;

    private Class<? extends IRequestablePage> responsePage = ProjectListPage.class;
    private LoadableDetachableModel<Project> projectModel;

    public AuthProjectPage(PageParameters pageParameters) {
        super(pageParameters);

        final StringValue projectIdParam = pageParameters.get("projectId");
        projectModel = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                return projectService.findOne(new ProjectId(projectIdParam.toString()));
            }
        };
        if (projectModel.getObject() == null) {
            setResponsePage(ProjectListPage.class);
        }

        if (!projectService.isAuthorized(getUser().getId(), getProjectId(), PermissionType.READ)) {
            WebSession.get().error("You are not allowed to access this project!");
            setResponsePage(ProjectListPage.class);
        }
    }

    /**
     * @return The project id
     */
    public ProjectId getProjectId() {
        return getProject().getId();
    }

    /**
     * Get the project identified by the id value in ProjectPage
     *
     * @return The current project
     */
    public Project getProject() {
        return projectModel.getObject();
    }
}
