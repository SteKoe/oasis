package de.stekoe.idss.page.project;

import de.stekoe.idss.component.BootstrapPagingNavigator;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.enums.ProjectStatus;
import de.stekoe.idss.model.provider.ProjectDataProvider;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.JavascriptEventConfirmation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectListPage extends AuthUserPage {

    @SpringBean private ProjectService projectService;

    @SpringBean private ProjectRoleService projectRoleService;

    @SpringBean private ProjectDataProvider projectDataProvider;

    private static final int ENTRIES_PER_PAGE = 20;

    public ProjectListPage() {
        addCreateProjectLink();
        addProjectList();
    }

    private void addProjectList() {

        DataView<Project> listView = new DataView<Project>("projectItem", projectDataProvider) {
            @Override
            protected void populateItem(Item<Project> item) {
                final Project project = item.getModelObject();
                PageParameters pageDetailsParameters = new PageParameters();
                pageDetailsParameters.add("id", project.getId());

                final Label projectTitleLabel = new Label("project.title", Model.of(project.getName()));
                item.add(projectTitleLabel);

                final Label projectStatusLabel = new Label("project.status", Model.of(getString(project.getProjectStatus().getKey())));
                item.add(projectStatusLabel);
                projectStatusLabel.setVisible(!project.getProjectStatus().equals(ProjectStatus.UNDEFINED));

                // Details link
                BookmarkablePageLink<ProjectDetailsPage> detailsPage = new BookmarkablePageLink<ProjectDetailsPage>("button.project.details", ProjectDetailsPage.class, pageDetailsParameters);
                item.add(detailsPage);
                detailsPage.setBody(Model.of(getString("label.details")));

                // Edit link
                final Link<Void> deleteProjectLink = new Link<Void>("button.project.delete") {
                    @Override
                    public IModel<?> getBody() {
                        return Model.of(getString("label.delete"));
                    }

                    @Override
                    public void onClick() {
                        projectService.delete(project.getId());
                    }
                };
                item.add(deleteProjectLink);
                deleteProjectLink.add(new JavascriptEventConfirmation("onClick", String.format(getString("project.delete.confirm"), project.getName())));
                deleteProjectLink.setVisible(projectService.isAuthorized(WebSession.get().getUser().getId(), project.getId(), PermissionType.DELETE));
            }
        };
        listView.setItemsPerPage(ENTRIES_PER_PAGE);
        add(listView);

        // Empty List indicator
        add(new Label("noItemLabel", getString("table.empty")) {
            @Override
            public boolean isVisible() {
                return false;
            }
        });

        add(new BootstrapPagingNavigator("navigator", listView));
    }

    private void addCreateProjectLink() {
        final BookmarkablePageLink<ProjectCreatePage> createProject = new BookmarkablePageLink<ProjectCreatePage>("createProject", ProjectCreatePage.class);
        add(createProject);
    }

    private void deleteProject(String id) {

    }
}
