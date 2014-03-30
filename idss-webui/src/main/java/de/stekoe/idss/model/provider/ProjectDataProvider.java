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

package de.stekoe.idss.model.provider;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectDataProvider implements IDataProvider<Project> {

    @Inject
    ProjectService projectService;

    @Override
    public Iterator<? extends Project> iterator(long first, long count) {
        return projectService.findAll().iterator();
    }

    @Override
    public long size() {
        return projectService.findByUser(WebSession.get().getUser().getId()).size();
    }

    @Override
    public IModel<Project> model(final Project project) {
        return new Model(project);
    }

    @Override
    public void detach() {
        // NOP
    }
}
