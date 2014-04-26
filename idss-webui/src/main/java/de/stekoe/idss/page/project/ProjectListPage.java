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

package de.stekoe.idss.page.project;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.provider.ProjectDataProvider;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.PaginationConfigurator;
import de.stekoe.idss.page.component.DataListView;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.DeleteLink;
import de.stekoe.idss.wicket.JavascriptEventConfirmation;

public class ProjectListPage extends AuthUserPage {

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private ProjectRoleService projectRoleService;

    @SpringBean
    private PaginationConfigurator paginationConfigurator;

    @SpringBean
    private ProjectDataProvider projectDataProvider;

    public ProjectListPage() {
        addCreateProjectLink();
        addProjectList();
    }

    private void addProjectList() {
        DataListView<Project> dataListView = new DataListView<Project>("project.list", projectDataProvider, paginationConfigurator.getValueFor(getClass())) {
            @Override
            protected List<? extends Link> getButtons(final Project modelObject) {

                DeleteLink deleteLink = new DeleteLink(DataListView.BUTTON_ID) {
                    @Override
                    public void onClick() {
                        projectService.delete(modelObject.getId());
                        setResponsePage(getPage());
                    }
                };
                deleteLink.add(new AttributeAppender("class", " btn-xs"));
                deleteLink.add(new JavascriptEventConfirmation("onClick", String.format(getString("project.delete.confirm"), modelObject.getName())));
                final boolean isAuthorized = projectService.isAuthorized(WebSession.get().getUser().getId(), modelObject.getId(), PermissionType.DELETE);
                deleteLink.setVisible(isAuthorized);

                PageParameters pageDetailsParameters = new PageParameters();
                pageDetailsParameters.add("projectId", modelObject.getId());
                BookmarkablePageLink<ProjectDetailsPage> detailsPageLink = new BookmarkablePageLink<ProjectDetailsPage>(DataListView.BUTTON_ID, ProjectDetailsPage.class, pageDetailsParameters);
                detailsPageLink.setBody(Model.of(getString("label.details")));
                detailsPageLink.add(new AttributeModifier("class", "btn btn-default btn-xs"));

                return Arrays.asList(detailsPageLink, deleteLink);
            }
        };
        add(dataListView);
    }

    private void addCreateProjectLink() {
        final BookmarkablePageLink<ProjectCreatePage> createProject = new BookmarkablePageLink<ProjectCreatePage>("createProject", ProjectCreatePage.class);
        add(createProject);
    }

    /**
     * DataView for the projects
     */
    private class ProjectDataView extends DataView<Project> {

        protected ProjectDataView(String id, IDataProvider<Project> dataProvider, long itemsPerPage) {
            super(id, dataProvider, itemsPerPage);
        }

        @Override
        protected void populateItem(Item<Project> item) {
            final Project project = item.getModelObject();
            PageParameters pageDetailsParameters = new PageParameters();
            pageDetailsParameters.add("projectId", project.getId());

            final Label projectTitleLabel = new Label("project.title", Model.of(project.getName()));
            item.add(projectTitleLabel);

            final Label projectStatusLabel = new Label("project.status", Model.of(getString(project.getProjectStatus().getKey())));
            item.add(projectStatusLabel);

            // Details link
            BookmarkablePageLink<ProjectDetailsPage> detailsPage = new BookmarkablePageLink<ProjectDetailsPage>("button.project.details", ProjectDetailsPage.class, pageDetailsParameters);
            item.add(detailsPage);
            detailsPage.setBody(Model.of(getString("label.details")));

            // Delete link
            final Link<Void> deleteProjectLink = new Link<Void>("button.project.delete") {
                @Override
                public IModel<?> getBody() {
                    return Model.of(getString("label.delete"));
                }

                @Override
                public void onClick() {
                    projectService.delete(project.getId());
                    success(getString("message.delete.success"));
                }
            };
            item.add(deleteProjectLink);
            deleteProjectLink.add(new JavascriptEventConfirmation("onClick", String.format(getString("project.delete.confirm"), project.getName())));
            final String userId = WebSession.get().getUser().getId();
            final String projectId = project.getId();
            final boolean isAuthorized = projectService.isAuthorized(userId, projectId, PermissionType.DELETE);
            deleteProjectLink.setVisible(isAuthorized);
        }
    }
}
