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

package de.stekoe.idss.page.project.role;

import java.util.Set;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.project.ProjectPage;

public class ProjectRoleEditPage extends ProjectPage {

    public ProjectRoleEditPage(PageParameters pageParameters) {
        super(pageParameters);
        add(new ProjectRoleForm("projectRoleEditForm", getProjectId(), getProjectRoleIdFromProject(pageParameters)));
    }

    private String getProjectRoleIdFromProject(PageParameters pageParameters) {
        final Set<ProjectRole> projectRoles = getProject().getProjectRoles();
        for (ProjectRole projectRole : projectRoles) {
            if (projectRole.getId().equals(pageParameters.get("roleId").toString())) {
                return projectRole.getId();
            }
        }
        return null;
    }
}
