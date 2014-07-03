package de.stekoe.idss.page.project;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.page.component.form.project.EditProjectForm;
import de.stekoe.idss.service.ProjectService;

public class ProjectEditPage extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    private static final Logger LOG = Logger.getLogger(ProjectEditPage.class);

    public ProjectEditPage(PageParameters params) {
        super(params);

        projectService.isAuthorized(getUser().getId(), getProjectId(), PermissionType.UPDATE);

        add(new EditProjectForm("form.project.edit", getProjectId()));

        addLinkProjectDelete();
    }

    private void addLinkProjectDelete() {
        final Link<Void> linkProjectDelete = new Link<Void>("link.project.delete") {
            @Override
            public void onClick() {
            }
        };
        add(linkProjectDelete);
        linkProjectDelete.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.DELETE));
    }
}
