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

import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.page.component.form.project.EditProjectMembersForm;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectMemberListPage extends ProjectPage {

    public ProjectMemberListPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new EditProjectMembersForm("form.project.members.edit", getProjectId()));
    }
}
